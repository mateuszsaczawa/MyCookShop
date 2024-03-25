package uk.ac.aber.mycookshop.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.ac.aber.mycookshop.hardcodedData.ProductModel
import uk.ac.aber.mycookshop.model.Call
import uk.ac.aber.mycookshop.model.CallQueue
import uk.ac.aber.mycookshop.model.CallQueue.addCall
import uk.ac.aber.mycookshop.model.MultiplierEnum
import uk.ac.aber.mycookshop.model.ProductStatus
import uk.ac.aber.mycookshop.ui.clock.model.GameTime
import java.text.SimpleDateFormat
import java.util.*

class ProductionViewModel : ViewModel() {

    // Global clock
    private val _gameTime = GameTime()
    val gameTime = MutableStateFlow(_gameTime)
    private val _timer = MutableStateFlow(_gameTime.getPlayTimeSeconds())
    val timer = _timer.asStateFlow()

    // Production clocks
    private val _timersMap = mutableMapOf<Int, Job>() // Mapa przechowująca identyfikatory timera i odpowiadające im joby

    private var nextTimerId = 0 // Identyfikator kolejnego timera

    private var days = MutableStateFlow(_gameTime.getDay())

    // Production variables
    private val _callRows = mutableStateListOf<CallQueue>()
    val callRows: List<CallQueue> = _callRows
    private val callQueue = CallQueue

    // Mapowanie Call na odpowiadające mu źródło czasu
    private val _callTimeMap = mutableMapOf<Call, MutableStateFlow<Long>>()
    val callTimeMap: MutableMap<Call, MutableStateFlow<Long>> = _callTimeMap

    private var multiplier = _gameTime.getMultiplier()

    private val _isAlertDialogOpen = MutableStateFlow(false)
    val isAlertDialogOpen: StateFlow<Boolean> = _isAlertDialogOpen



    init {
        startGlobalTimer()
    }

    private fun startGlobalTimer() {

        // Start głównego timera aplikacji
        viewModelScope.launch {
            while (true) {
                delay( (1 * multiplier).toLong())

                // Update the game day
                if (!isAlertDialogOpen.value) {

                    _timer.value += 1

                    if (_timer.value == 82800L) {
                        _gameTime.increaseDay()
                        _timer.value = 26100
                        setAlertDialogOpen(true)
                    }
                }
            }
        }
    }

    // Metoda do dodawania nowego timera
    fun addNewTimer(call: Call) {
        val timerId = nextTimerId++

        val job = viewModelScope.launch {
            while (_callTimeMap[call]!!.value > 0) {
                delay((1 * multiplier).toLong())
                _callTimeMap[call]!!.value -= 1


            }
        }
//        _timersMap[timerId] = job
    }

    // Metoda do dodawania elementów do kolejki
    fun addNewCall(product: ProductModel, amount: Int) {
        val newCall = Call(product, amount, ProductStatus.PREPARATION)

        // Tworzenie osobnego źródła czasu dla każdego nowego Call
        val callTime = MutableStateFlow(newCall.product.cookingTimeInSeconds)
        _callTimeMap[newCall] = callTime

        addNewTimer(newCall)
        if(amount != 0){
            addCall(newCall)
            _callRows.add(callQueue)

        }
    }

    fun changeMultiplier(mult: String){
        if (mult== "Slow") {
            _gameTime.setMultiplier(2F)
            multiplier = _gameTime.getMultiplier()
        }
        if (mult== "Normal") {
            _gameTime.setMultiplier(1F)
            multiplier = _gameTime.getMultiplier()
        }
        if (mult== "Fast") {
            _gameTime.setMultiplier(0.5F)
            multiplier = _gameTime.getMultiplier()
        }
    }

    fun setAlertDialogOpen(change: Boolean){
        _isAlertDialogOpen.value = change
    }
}
package uk.ac.aber.mycookshop.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.ac.aber.mycookshop.hardcodedData.ProductModel
import uk.ac.aber.mycookshop.model.Call
import uk.ac.aber.mycookshop.model.CallQueue
import uk.ac.aber.mycookshop.model.CallQueue.addCall
import uk.ac.aber.mycookshop.model.ProductStatus
import uk.ac.aber.mycookshop.ui.clock.model.GameTime
import java.text.SimpleDateFormat
import java.util.*

class ProductionViewModel : ViewModel() {

    //clock variables
    private val _gameTime = GameTime()
    private val _timer = MutableStateFlow(_gameTime)
    var modi = _gameTime.getMultiplier() * 1000
    val timer = _timer.asStateFlow()
    private var timerJob: Job? = null

    //production variables
    private val _callRows = mutableStateListOf<CallQueue>()
    val callRows: List<CallQueue> = _callRows
    private val callQueue = CallQueue

    init {
        startTimer()
    }

    private fun startTimer() {
//        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            repeat(Int.MAX_VALUE) {
                delay(modi)
                _gameTime.updatePlayDay() // Aktualizacja dnia gry
                _timer.value = _gameTime
            }
        }
    }

    // Metoda do dodawania elementów do kolejki
    fun addNewCall(product: ProductModel, amount: Int) {
        val newCall = Call(product, amount, ProductStatus.PREPARATION)
        addCall(newCall)

        _callRows.add(callQueue)

    }
}
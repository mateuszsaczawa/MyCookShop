package uk.ac.aber.mycookshop.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.ac.aber.mycookshop.model.ProductModel
import uk.ac.aber.mycookshop.model.*
import uk.ac.aber.mycookshop.model.CallQueue.addCall
import uk.ac.aber.mycookshop.ui.clock.model.GameTime

class ProductionViewModel : ViewModel() {

    // Global clock
    private val _gameTime = GameTime()
    val gameTime = MutableStateFlow(_gameTime)
    private val _timer = MutableStateFlow(_gameTime.getPlayTimeSeconds())
    val timer = _timer.asStateFlow()
    private val _isStopped = MutableStateFlow(false)
    private var multiplier = _gameTime.getMultiplier()

    // Production clocks
    private val _callTimeMap = mutableMapOf<Call, MutableStateFlow<Long>>()
    val callTimeMap: MutableMap<Call, MutableStateFlow<Long>> = _callTimeMap

    private val _queueProductMap = mutableMapOf<ProductType, MutableStateFlow<Call>>()
    val queueProductMap: MutableMap<ProductType, MutableStateFlow<Call>> = _queueProductMap

    // Production variables
//    val totalProductList = mutableStateOf(enumValues<ProductType>().associateWith { 0 })

    val totalAmountList = mutableStateOf<MutableMap<Pair<ProductType, ProductStatus>, Int>>(mutableMapOf())


//    val totalAmountList = mutableStateOf<MutableList<Pair<ProductType, Int>>>(mutableListOf())
    val total = mutableMapOf<ProductType, MutableState<Int>>()
    private val _onHandMap = mutableMapOf<ProductType,Int>()
    private val _wasteMap = mutableMapOf<ProductType,Int>()
    private val _soldMap = mutableMapOf<ProductType,Int>()
    private val callQueue = CallQueue

    // Alert
    private val _isAlertDialogOpen = MutableStateFlow(false)
    val isAlertDialogOpen: StateFlow<Boolean> = _isAlertDialogOpen


    fun testowaFunkcjaPrintln(productType: ProductType) {

    }


    init {
        startGlobalTimer()

        // Inicjalizacja ilości produktów dla każdej pary (ProductType, ProductStatus)
        val initialCounts = mutableMapOf<Pair<ProductType, ProductStatus>, Int>()
        ProductType.values().forEach { productType ->
            ProductStatus.values().forEach { productStatus ->
                initialCounts[Pair(productType, productStatus)] = 0
            }
        }
        totalAmountList.value = initialCounts
    }

    private fun startGlobalTimer() {

        // Start głównego timera aplikacji
        viewModelScope.launch {
            while (true) {
                delay( (10 * multiplier).toLong())

                // Update the game day
                if (!_isAlertDialogOpen.value && !_isStopped.value) {

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

        val job = viewModelScope.launch {
            while (_callTimeMap[call]!!.value > 0) {
                delay((1 * multiplier).toLong())
                if (!_isAlertDialogOpen.value && !_isStopped.value) {
                    _callTimeMap[call]!!.value -= 1
                }
            }

            call.status = ProductStatus.READY
            addToCountForProductType(call.product.type, ProductStatus.TOTAL, call.ilosc)
            addToCountForProductType(call.product.type, ProductStatus.READY, call.ilosc)


        }
    }

    // Metoda do dodawania elementów do kolejki`
    fun addNewCall(product: ProductModel, amount: Int) {
        val newCall = Call(product, amount, ProductStatus.PREPARATION)

        // Tworzenie osobnego źródła czasu dla każdego nowego Call
        val callTime = MutableStateFlow(newCall.product.cookingTimeInSeconds)

        if(amount != 0){

            _queueProductMap[product.type] = MutableStateFlow(newCall)
            _callTimeMap[newCall] = callTime

            addNewTimer(newCall)

            addCall(newCall)
//            _callRows.add(callQueue)




        }
    }



    fun changeMultiplier(mult: MultiplierEnum){
        if (mult== MultiplierEnum.SLOW) {
            _gameTime.setMultiplier(2F)
            multiplier = _gameTime.getMultiplier()
            _isStopped.value = false
        }
        if (mult== MultiplierEnum.NORMAL) {
            _gameTime.setMultiplier(1F)
            multiplier = _gameTime.getMultiplier()
            _isStopped.value = false
        }
        if (mult== MultiplierEnum.FAST) {
            _gameTime.setMultiplier(0.5F)
            multiplier = _gameTime.getMultiplier()
            _isStopped.value = false
        }
        if (mult== MultiplierEnum.STOP) {
            _isStopped.value = true
        }

    }

    fun setAlertDialogOpen(change: Boolean){
        _isAlertDialogOpen.value = change
    }

    fun addToCountForProductType(productType: ProductType, productStatus: ProductStatus, amount: Int) {
        val currentCounts = totalAmountList.value.toMutableMap()
        val currentCount = currentCounts.getOrDefault(Pair(productType, productStatus), 0)
        currentCounts[Pair(productType, productStatus)] = currentCount + amount
        totalAmountList.value = currentCounts
    }

    fun getTotalAmountForProductTypeAndStatus(productType: ProductType, productStatus: ProductStatus): Int {
        val totalCounts = totalAmountList.value
        return totalCounts[Pair(productType, productStatus)] ?: 0
    }
}
package uk.ac.aber.mycookshop.viewModel

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
import uk.ac.aber.mycookshop.model.CallQueue.getOldestCallByType
import uk.ac.aber.mycookshop.model.CallQueue.removeByProductType
import uk.ac.aber.mycookshop.model.CallQueue.removeCall
import uk.ac.aber.mycookshop.model.order.MealList.combos
import uk.ac.aber.mycookshop.model.order.MealList.getSize
import uk.ac.aber.mycookshop.model.order.Order
import uk.ac.aber.mycookshop.model.order.OrderItem
import uk.ac.aber.mycookshop.ui.clock.model.GameTime
import uk.ac.aber.mycookshop.upgrades.Staff
import java.util.*
import kotlin.random.Random

class ProductionViewModel : ViewModel() {

    // Global clock
    private val _gameTime = GameTime()
    val gameTime = MutableStateFlow(_gameTime)
    private val _timer = MutableStateFlow(_gameTime.getPlayTimeSeconds())
    val timer = _timer.asStateFlow()
    private val _isStopped = MutableStateFlow(false)
    private var multiplier = _gameTime.getMultiplier()

    // Production clocks
    private val _callTimeMap = mutableMapOf<Call, MutableStateFlow<Int>>()
    val callTimeMap: MutableMap<Call, MutableStateFlow<Int>> = _callTimeMap//////////////////////////////////////////////////////////

    //koeljka z podziałem na ProductType, ktory zawiera wszystkie call dla tego rodzaju produktu
    private val _queueProductMap = mutableMapOf<ProductType, MutableStateFlow<Call>>()
    val queueProductMap: MutableMap<ProductType, MutableStateFlow<Call>> = _queueProductMap

    // Production variables
    val totalAmountList = mutableStateOf<MutableMap<Pair<ProductType, ProductStatus>, Int>>(mutableMapOf())

    //waste
    private val _isWastable = mutableMapOf<Call, Boolean>()
    val isWastable: MutableMap<Call, Boolean> = _isWastable

    // Alert
    private val _isAlertDialogOpen = MutableStateFlow(false)
    val isAlertDialogOpen: StateFlow<Boolean> = _isAlertDialogOpen

    //Orders
    private val _orderList = MutableStateFlow<List<Order>>(emptyList())                                                     //zmienic to na mape zamiast listy
    val orderList = _orderList.asStateFlow()////////////////////

    private val _orderTimeMap = mutableMapOf<Order, MutableStateFlow<Int>>()
    val orderTime: MutableMap<Order, MutableStateFlow<Int>> = _orderTimeMap

    //upgrades
    private val _staff = Staff()
//    val staff = MutableStateFlow(_staff)


    /*
                                            Global timer section
    */

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

                    if(_timer.value >= 27000) {
                        if(_timer.value % 90 == 0L) {
                            var peak = 0
                            if(isPeak(_timer.value)) {
                                peak = 2
                            }
                            generateOrders(peak)
                        }
                    }


                    if (_timer.value == 82800L) {
                        _gameTime.increaseDay()
                        _timer.value = 26100
                        setAlertDialogOpen(true)
                    }
                }
            }
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

    /*
                                             Call/ call timer/ waste section
     */

    // Metoda do dodawania nowego timera
    private fun addNewTimer(call: Call) {

        val timeForTimer = when (call.status) {
            ProductStatus.PREPARATION -> MutableStateFlow(call.product.cookingTimeInSeconds)
            ProductStatus.READY -> MutableStateFlow(call.product.expiryTimeInSeconds)
            else -> MutableStateFlow(0)
        }

        _callTimeMap[call] = timeForTimer

        viewModelScope.launch {
            while (_callTimeMap[call]!!.value > 0) {
                delay((1 * multiplier).toLong())
                if (!_isAlertDialogOpen.value && !_isStopped.value) {
                    _callTimeMap[call]!!.value -= 1
                }
            }

            if(call.status == ProductStatus.PREPARATION) {
                call.status = ProductStatus.READY
                addToCountForProductType(call.product.type, ProductStatus.TOTAL, call.ilosc)
                addToCountForProductType(call.product.type, ProductStatus.READY, call.ilosc)

                addNewTimer(call)
            }

            else if(call.status == ProductStatus.READY)
            {
                _queueProductMap[call.product.type]!!.value.status = ProductStatus.WASTABLE
                _isWastable[call] = true
            }
        }
    }

    // Metoda do dodawania elementów do kolejki`
    fun addNewCall(product: ProductModel, amount: Int) {
        val newCall = Call(product, amount, ProductStatus.PREPARATION)

        if(amount != 0){

            _queueProductMap[product.type] = MutableStateFlow(newCall)

            _isWastable[newCall] = false
            addNewTimer(newCall)

            addCall(newCall)
//            _callRows.add(callQueue)
        }
    }


    fun addToCountForProductType(productType: ProductType, productStatus: ProductStatus, amount: Int) {
        val currentCounts = totalAmountList.value.toMutableMap()
        val currentCount = currentCounts.getOrDefault(Pair(productType, productStatus), 0)
        currentCounts[Pair(productType, productStatus)] = currentCount + amount
        totalAmountList.value = currentCounts
    }
    fun subtractToCountForProductType(productType: ProductType, productStatus: ProductStatus, amount: Int) {
        val currentCounts = totalAmountList.value.toMutableMap()
        val currentCount = currentCounts.getOrDefault(Pair(productType, productStatus), 0)
        currentCounts[Pair(productType, productStatus)] = currentCount - amount
        totalAmountList.value = currentCounts
    }

    fun getTotalAmountForProductTypeAndStatus(productType: ProductType, productStatus: ProductStatus): Int {
        val totalCounts = totalAmountList.value
        return totalCounts[Pair(productType, productStatus)] ?: 0
    }

    fun removeCallByCall(call: Call) {

        removeCall(call)
        subtractToCountForProductType(call.product.type, ProductStatus.READY, call.ilosc)
        addToCountForProductType(call.product.type, ProductStatus.WASTE, call.ilosc)
    }

    fun removeCallByProductType(productType: ProductType) {
        val callOld = getOldestCallByType(productType)
        removeByProductType(productType)
        subtractToCountForProductType(callOld.product.type, ProductStatus.READY, callOld.ilosc)
        addToCountForProductType(callOld.product.type, ProductStatus.WASTE, callOld.ilosc)
    }

    /*
                                            Order section
    */

    fun isPeak(time: Long): Boolean {
        return (time in 43200..50400) || (time in 61200..720000)
    }

    //wygeneruj liczbe zamowien ile sie pojawi w ciagu 90 sekund
    fun generateOrders(peakModifier: Int){
        var randomOrderNumber = Random.nextInt(0 + peakModifier, 2 + peakModifier)

        do {
            generateRandomOrder()
            randomOrderNumber -= 1
        } while (randomOrderNumber >= 0)


    }

    //wygeneruj ile zestawow bedzie w zamowieniu
    fun generateRandomOrder() {
        val productsInOrder = LinkedList<OrderItem>()
        var mealsNumber = Random.nextDouble(0.0, 1.0)
        var howManyMeals = 0
        var upgradedHW = 0
        when {
            mealsNumber <= 0.35 -> howManyMeals = 0
            mealsNumber <= 0.75 -> howManyMeals = 1
            mealsNumber <= 0.93 -> howManyMeals = 2
            mealsNumber <= 0.98 -> howManyMeals = 3
            mealsNumber <= 1.0 ->  howManyMeals = 4
        }
        do {
            productsInOrder.add(generateMeal())

            val upgade = Random.nextInt(_staff.upgradesRatio, 100)

            when {
                upgade <= 50 -> upgradedHW += 0
                upgade <= 90 -> upgradedHW += 2
                upgade <= 100 -> upgradedHW += 4
            }

            println("wardega: mealNumber: " + howManyMeals)
            howManyMeals -= 1

        } while (howManyMeals >= 0)

        productsInOrder.add(OrderItem("Upgraded hotwings", ProductType.HOTWING, upgradedHW))

        productsInOrder.forEach { product ->
            product.price
        }
        val newOrder = Order(_timer.value, _gameTime.getDay(), false, 0, productsInOrder)
        _orderList.value =  _orderList.value + listOf(newOrder)

        addNewTimerForOrder(newOrder)

        println("orderList: $_orderList")

    }

    //funkcja generujaca meal

    fun generateMeal(): OrderItem {
        val mealNo = Random.nextInt(0, getSize()-1)

        return combos[mealNo]
    }

    private fun addNewTimerForOrder(order: Order) {

        if (order.dayAppear == _gameTime.getDay()) {
            _orderTimeMap[order] = MutableStateFlow(0)

            viewModelScope.launch {
                while (_orderTimeMap[order]!!.value > 0) {
                    delay((1 * multiplier).toLong())
                    if (!_isAlertDialogOpen.value && !_isStopped.value) {
                        _orderTimeMap[order]!!.value += 1
                    }
                }
            }
        }

    }

}

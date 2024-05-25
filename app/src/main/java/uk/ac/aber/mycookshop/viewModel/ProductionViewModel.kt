package uk.ac.aber.mycookshop.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import uk.ac.aber.mycookshop.ui.elements.Production.model.ProductList
import uk.ac.aber.mycookshop.ui.elements.Production.model.ProductModel
import uk.ac.aber.mycookshop.ui.elements.Production.model.CallQueue.addCall
import uk.ac.aber.mycookshop.ui.elements.Production.model.CallQueue.removeByProductType
import uk.ac.aber.mycookshop.ui.elements.Production.model.CallQueue.removeCall
import uk.ac.aber.mycookshop.ui.elements.Production.model.Call
import uk.ac.aber.mycookshop.ui.elements.Production.model.ProductStatus
import uk.ac.aber.mycookshop.ui.elements.Production.model.ProductType
import uk.ac.aber.mycookshop.ui.elements.orders.model.MealList.combos
import uk.ac.aber.mycookshop.ui.elements.orders.model.MealList.getSize
import uk.ac.aber.mycookshop.ui.elements.orders.model.Order
import uk.ac.aber.mycookshop.ui.elements.orders.model.OrderItem
import uk.ac.aber.mycookshop.ui.elements.clock.model.GameTime
import uk.ac.aber.mycookshop.ui.elements.clock.model.MultiplierEnum
import uk.ac.aber.mycookshop.ui.elements.orders.model.OrderQueue
import uk.ac.aber.mycookshop.ui.elements.orders.model.OrderQueue.addOrder
import uk.ac.aber.mycookshop.ui.elements.orders.model.OrderQueue.updateStatus
import uk.ac.aber.mycookshop.ui.elements.orders.model.OrderStatus
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
    private val _currentMultiplier = MutableStateFlow(MultiplierEnum.NORMAL)
    val currentMultiplier: StateFlow<MultiplierEnum> = _currentMultiplier.asStateFlow()

    // Production clocks
    private val _callTimeMap = mutableMapOf<Call, MutableStateFlow<Int>>()
    val callTimeMap: MutableMap<Call, MutableStateFlow<Int>> = _callTimeMap//////////////////////////////////////////////////////////

    /*
     a queue divided into ProductType, which contains all calls for this type of product
     the change is intended to determine the status of individual Calls divided into types of products
      */
    private val _queueProductMap = mutableMapOf<ProductType, MutableList<Call>>()
    val queueProductMap: MutableMap<ProductType, MutableList<Call>> = _queueProductMap

    // Production variables
    //a queue tracking how many products have a given status
    private val _totalAmountList = mutableStateOf<MutableMap<Pair<ProductType, ProductStatus>, Int>>(mutableMapOf())
    val totalAmountList: MutableState<MutableMap<Pair<ProductType, ProductStatus>, Int>> = _totalAmountList

    //waste
    private val _isWastable = mutableMapOf<Call, Boolean>()
    val isWastable: MutableMap<Call, Boolean> = _isWastable

    // Alert
    private val _isAlertDialogOpen = MutableStateFlow(true)
    val isAlertDialogOpen: StateFlow<Boolean> = _isAlertDialogOpen

    //Orders
    private val _orderList = MutableStateFlow<MutableList<Order>>( mutableListOf())
    val orderList: StateFlow<List<Order>> = _orderList.asStateFlow()

    private val _orderTimeMap = mutableMapOf<Order, MutableStateFlow<Int>>()
    val orderTimeMap: MutableMap<Order, MutableStateFlow<Int>> = _orderTimeMap

    //<order.id, <productType, amount>>
    private val _orderProductAmount = mutableStateOf<Map<Int, Map<ProductType, Int>>>(emptyMap())
    val orderProductAmount: MutableState<Map<Int, Map<ProductType, Int>>> = _orderProductAmount

    //
    private val _isOrderServiced = mutableMapOf<Order, MutableStateFlow<Boolean>>()
    val isOrderServiced: Map<Order, MutableStateFlow<Boolean>> = _isOrderServiced

    private val _selectedTab = MutableStateFlow(0)
    val selectedTab:StateFlow<Int> = _selectedTab.asStateFlow()

    private val _filteredOrders = MutableStateFlow<List<Order>>(mutableListOf())
    val filteredOrders: StateFlow<List<Order>> = _filteredOrders

    //first cook
    private val _productsFirstCook = mutableStateMapOf<ProductModel, Int>()
    val productsFirstCook: Map<ProductModel, Int> = _productsFirstCook

    //upgrades
    private val _staff = Staff()

                                                                    /*
                                            Global timer section
                                                                    */
    init {
        startGlobalTimer()

        // Initialization of the number of products for each pair (Product Type, Product Status)
        val initialCounts = mutableMapOf<Pair<ProductType, ProductStatus>, Int>()
        ProductType.values().forEach { productType ->
            ProductStatus.values().forEach { productStatus ->
                initialCounts[Pair(productType, productStatus)] = 0
            }
        }
        _totalAmountList.value = initialCounts

        viewModelScope.launch {
            combine(_selectedTab, _orderList) { tab, orders ->
                updateFilteredOrders(tab, orders)
            }.collect { filteredOrders ->
                _filteredOrders.value = filteredOrders.toMutableList()
            }
        }
    }

    private fun startGlobalTimer() {

        // Start the main application timer
        viewModelScope.launch {
            while (true) {
                delay( (100 * multiplier).toLong())

                // Update the game day
                if (!_isAlertDialogOpen.value && !_isStopped.value) {

                    _timer.value += 1

                    if(_timer.value >= 28800) {
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
            _currentMultiplier.value = mult
            multiplier = _gameTime.getMultiplier()
            _isStopped.value = false
        }
        if (mult== MultiplierEnum.NORMAL) {
            _gameTime.setMultiplier(1F)
            _currentMultiplier.value = mult
            multiplier = _gameTime.getMultiplier()
            _isStopped.value = false
        }
        if (mult== MultiplierEnum.FAST) {
            _gameTime.setMultiplier(0.5F)
            _currentMultiplier.value = mult
            multiplier = _gameTime.getMultiplier()
            _isStopped.value = false
        }
        if (mult== MultiplierEnum.STOP) {
            _isStopped.value = true
            _currentMultiplier.value = mult
        }

    }
/*
                                            Alert
 */
    fun setAlertDialogOpen(change: Boolean){
        _isAlertDialogOpen.value = change
    }

                                                                                /*
                                             Call/ call timer/ waste section
                                                                                */

    // Method for adding a new timer for a new call
    private fun addNewTimer(call: Call) {

        _callTimeMap.getOrPut(call) {
            MutableStateFlow(when (call.status) {
                ProductStatus.PREPARATION -> call.product.cookingTimeInSeconds
                ProductStatus.READY -> call.product.expiryTimeInSeconds
                else -> 0
            })
        }

        viewModelScope.launch {
            while (_callTimeMap[call]!!.value > 0) {
                delay((100 * multiplier).toLong())
                if (!_isAlertDialogOpen.value && !_isStopped.value) {
                    _callTimeMap[call]!!.value -= 1
                }
            }
            when (call.status) {
                ProductStatus.PREPARATION -> {
                    call.status = ProductStatus.READY
                    _callTimeMap[call]!!.value = call.product.expiryTimeInSeconds
                    _queueProductMap[call.product.type]?.find { it == call }?.let {
                        it.status = ProductStatus.READY
                    }
                    addToCountForProductType(call.product.type, ProductStatus.TOTAL, call.amount)
                    addToCountForProductType(call.product.type, ProductStatus.READY, call.amount)
                    addNewTimer(call)
                }
                ProductStatus.READY -> {
                    call.status = ProductStatus.WASTABLE
                    _queueProductMap[call.product.type]?.find { it == call }?.let {
                        it.status = ProductStatus.WASTABLE
                    }
                    addNewTimer(call)
                }
                else -> {
                    _isWastable[call] = true
                }
            }
        }
    }

    // Method for adding items to the queue
    fun addNewCall(product: ProductModel, amount: Int) {
        if(amount != 0){

            val newCall = Call(product, amount, ProductStatus.PREPARATION)

            val callList = _queueProductMap.getOrPut(product.type) { mutableListOf() }
            callList.add(newCall)

            _isWastable[newCall] = false
            addNewTimer(newCall)

            addCall(newCall)
        }
    }

    fun addToCountForProductType(productType: ProductType, productStatus: ProductStatus, amount: Int) {
        val currentCounts = _totalAmountList.value.toMutableMap()
        val currentCount = currentCounts.getOrDefault(Pair(productType, productStatus), 0)
        currentCounts[Pair(productType, productStatus)] = currentCount + amount
        _totalAmountList.value = currentCounts
    }
    fun subtractToCountForProductType(productType: ProductType, productStatus: ProductStatus, amount: Int) {
        val currentCounts = _totalAmountList.value.toMutableMap()
        val currentCount = currentCounts.getOrDefault(Pair(productType, productStatus), 0)
        currentCounts[Pair(productType, productStatus)] = currentCount - amount
        _totalAmountList.value = currentCounts
    }

    fun removeCallByCall(call: Call, oldStatus: ProductStatus, newStatus: ProductStatus) {

        val callOld = findSmallestId(call.product.type,0)

        if (callOld != null) {
            if (callOld.status == ProductStatus.WASTABLE) {
                removeCall(call)
                subtractToCountForProductType(call.product.type, oldStatus, call.amount)
                addToCountForProductType(call.product.type, newStatus, call.amount)
                callOld.status = ProductStatus.WASTE
            }
        }
    }

    fun removeCallByProductType(productType: ProductType) {

        val callOld = findSmallestId(productType,0)

        if (callOld != null) {
            if (callOld.status == ProductStatus.WASTABLE){
                removeByProductType(productType)
                subtractToCountForProductType(callOld.product.type, ProductStatus.READY, callOld.amount)
                addToCountForProductType(callOld.product.type, ProductStatus.WASTE, callOld.amount)
                callOld.status = ProductStatus.WASTE
            }
        }

    }

                                                                                /*
                                            Order section
                                                                                 */

    fun isPeak(time: Long): Boolean {
        return (time in 43200..50400) || (time in 61200..720000)
    }

    //generate the number of orders that will appear within 90 seconds
    fun generateOrders(peakModifier: Int){
        var randomOrderNumber = Random.nextInt(0 + peakModifier, 2 + peakModifier)

        do {
            generateRandomOrder()
            randomOrderNumber -= 1
        } while (randomOrderNumber >= 0)
    }

    //generate how many sets will be in the order
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

            howManyMeals -= 1

        } while (howManyMeals >= 0)

        productsInOrder.add(OrderItem("Upgraded hotwings", ProductType.HOTWING, upgradedHW))

        productsInOrder.forEach { product ->
            product.price
        }
        val newOrder = Order(_timer.value, _gameTime.getDay(), false, 0, productsInOrder)

        val currentOrders = _orderList.value.toMutableList()
        currentOrders.add(newOrder)
        _orderList.value = currentOrders

        addNewTimerForOrder(newOrder)

        addOrder(newOrder)
        addToOrderList(newOrder)
        sellProductsFromOrder(newOrder)
        _isOrderServiced[newOrder] = MutableStateFlow(false)

    }

    //meal generating function

    fun generateMeal(): OrderItem {
        val mealNo = Random.nextInt(0, getSize()-1)

        return combos[mealNo]
    }

    private fun addNewTimerForOrder(order: Order) {

        if (order.dayAppear == _gameTime.getDay()) {
            _orderTimeMap[order] = MutableStateFlow(0)

            viewModelScope.launch {
                while (_orderTimeMap[order]!!.value >= 0) {
                    delay((100 * multiplier).toLong())
                    if (!_isAlertDialogOpen.value && !_isStopped.value) {
                        _orderTimeMap[order]!!.value += 1
                    }
                }
            }
        }

    }

    fun addToOrderList(order: Order) {
        val currentMap = _orderProductAmount.value.toMutableMap()
        val productMap = order.products.groupBy { it.product }
            .mapValues { (_, items) -> items.sumOf {it.amount } }

        currentMap[order.id] = productMap

        _orderProductAmount.value = currentMap
    }

    //checks whether the order can be prepared immediately when an order is placed
    private fun canBeServed(order: Order): Boolean {

        val orderNeeds = _orderProductAmount.value[order.id] ?: return false

        return orderNeeds.all { (productType, requiredQuality) ->
            val onHandKeyReady = Pair(productType, ProductStatus.READY)
            val onHandKeyWastable = Pair(productType, ProductStatus.WASTABLE)
            val availableQuantity =
                _totalAmountList.value.getOrDefault(onHandKeyReady, 0) +
                _totalAmountList.value.getOrDefault(onHandKeyWastable, 0)

            availableQuantity >= requiredQuality

        }
    }

    fun sellProductsFromOrder(order: Order) {

        //variable with order content
        val orderContents = _orderProductAmount.value[order.id]

        if (orderContents == null || !canBeServed(order)) {
            return
        }
        orderContents?.forEach { (productType, amount) ->

            var soldAmount = amount
            var whichTime = 0

            while ( soldAmount > 0 ) {
                val callEntry = findSmallestId(productType, whichTime)
                if(callEntry == null) break

                //if it's enough products from this Call just subtract amount
                if (callEntry.amount >= soldAmount) {

                    callEntry.amount -= soldAmount
                    updateProductCounts(callEntry, soldAmount)
                    soldAmount = 0
                    if(callEntry.amount == 0) {
                        removeCallByCall(callEntry, callEntry.status, ProductStatus.SOLD)
                    }
                    //if not then subtract what you can and then take next Call
                } else {

                    soldAmount -= callEntry.amount
                    updateProductCounts(callEntry, callEntry.amount)
                    removeCallByCall(callEntry, callEntry.status, ProductStatus.SOLD)
                    whichTime++
                }
            }
            }
            markOrderAsServiced(order)
        updateStatus(order.id, OrderStatus.SERVICED)

    }
    private fun updateProductCounts(call: Call, amountSold: Int) {
        subtractToCountForProductType(call.product.type, call.status, amountSold)
        addToCountForProductType(call.product.type, ProductStatus.SOLD, amountSold)
    }

    fun findSmallestId(productType: ProductType, whichTime: Int): Call? {

        val calls = _queueProductMap[productType]

        val eligibleCalls = calls
            ?.filter { call -> call.status == ProductStatus.READY || call.status == ProductStatus.WASTABLE }
            ?.sortedBy { call -> call.id }

        if (eligibleCalls == null || eligibleCalls.size < whichTime) {
            throw IllegalArgumentException("Not enough calls available")
        }

        return eligibleCalls.elementAtOrNull(whichTime)

    }

    fun markOrderAsServiced(order: Order) {
        _isOrderServiced[order]?.value = true
    }

    fun setSelectedTab(tab: Int) {
        _selectedTab.value = tab
    }

    private fun updateFilteredOrders(tab: Int, orders: List<Order>): List<Order> {
        return  if (tab == 0) {
            orders.filter { _isOrderServiced[it]?.value ?: true }
        } else {
            orders.filter { _isOrderServiced[it]?.value ?: false }
        }
    }

    private fun filterOrdersByStatus() {
        val status = if (selectedTab.value == 0 ) OrderStatus.PENDING else OrderStatus.SERVICED
        _filteredOrders.value = OrderQueue.getAllOrders().filter { it.status == status }
    }
                                                                                /*
                            First Cook
                                                                                */
    fun updateProductFirstCook(product: ProductModel, callAmount: Int) {
        _productsFirstCook[product] = callAmount
    }

    fun doFirstCook() {
        _productsFirstCook.forEach { (productType, amount) ->

            val newCall = Call(productType, amount, ProductStatus.READY)

            val callsList = _queueProductMap.getOrPut(productType.type) { mutableListOf() }
            callsList.add(newCall)

            _isWastable[newCall] = false

            addToCountForProductType(productType.type, ProductStatus.READY, amount)
            addToCountForProductType(productType.type, ProductStatus.TOTAL, amount)

            addNewTimer(newCall)

            addCall(newCall)
        }
    }
    fun loadPreviousOrDefultFirstCook() {

        if(_gameTime.getDay() <= 1) {

            ProductList.productList.forEach { product ->
                _productsFirstCook[product] = 99
            }
            doFirstCook()
        }
        else {
            doFirstCook()
        }
    }
}

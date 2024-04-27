package uk.ac.aber.mycookshop.ui.orders.model

import java.util.*
import kotlin.random.Random


class Order(
    val id: Int,
    val timeAppear: Long,
    val dayAppear: Int,
    var status: OrderStatus,
    var isServiced: Boolean,
    val serviceTime: Int = 0,
    var cost: Double,
    var products: LinkedList<OrderItem>
) {
    companion object {
        private var nextId = 1

        private fun generateNextId(): Int {
            return nextId++
        }
    }

    constructor(
        timeAppear: Long,
        dayAppear: Int,
        isServiced: Boolean,
        serviceTime: Int,
        products: LinkedList<OrderItem>
    ) : this(generateNextId(), timeAppear, dayAppear, OrderStatus.PENDING, isServiced, serviceTime, 0.0, products) {
        var totalCost = 0.0
        for (item in products) {
            totalCost += item.price

        }

        this.cost = totalCost
    }

    fun orderServiced() {
        isServiced = true
    }


    fun generateRandomOrder() : Int {
        val random = Random.nextDouble(0.0, 1.0)

        return when {
            random < 0.2 -> 0 // 20% szansa na 0
            random < 0.6 -> 1 // 40% szansa na 1 (łącznie 20% + 40% = 60%)
            random < 0.9 -> 2 // 30% szansa na 2 (łącznie 60% + 30% = 90%)
            else -> 3 // 10% szansa na 3 (łącznie 90% + 10% = 100%)
        }
    }

    fun getProductsFromOrder(): LinkedList<OrderItem> {

        return products
    }
}


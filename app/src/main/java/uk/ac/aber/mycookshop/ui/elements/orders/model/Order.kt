package uk.ac.aber.mycookshop.ui.elements.orders.model

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
    ) : this(
        generateNextId(), timeAppear, dayAppear,
        OrderStatus.PENDING, isServiced, serviceTime, 0.0, products) {
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
            random < 0.2 -> 0 // 20% chance of 0
            random < 0.6 -> 1 // 40% chance of 1 (total 20% + 40% = 60%)
            random < 0.9 -> 2 // 30% chance of 2 (total 60% + 30% = 90%)
            else -> 3 // 10% chance of 3 (total 90% + 10% = 100%)
        }
    }

    fun getProductsFromOrder(): LinkedList<OrderItem> {

        return products
    }
}


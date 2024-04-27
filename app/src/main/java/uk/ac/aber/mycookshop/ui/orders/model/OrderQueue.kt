package uk.ac.aber.mycookshop.ui.orders.model

import java.util.*

object OrderQueue {
    private val orderQueue: LinkedList<Order> = LinkedList()

    fun addOrder(order: Order) {
        orderQueue.add(order)
    }

    fun getAllOrders(): List<Order> {
        return orderQueue.toList()
    }

    fun getOrdersWithStatus(status: OrderStatus): List<Order>{
        return orderQueue.filter { it.status == status }
    }

    fun updateStatus(orderId: Int, newStatus: OrderStatus) {
        val order = orderQueue.find { it.id == orderId}
        if (order != null) {
            order.status = newStatus
        }
    }
}
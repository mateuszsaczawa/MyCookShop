package uk.ac.aber.mycookshop.model

import java.util.*

object CallQueue {
    private val queue: LinkedList<Call> = LinkedList()

    // Add a call to the queue.
    fun addCall(call: Call) {
        queue.offer(call)
    }

    // Metoda zwracająca wszystkie elementy z kolejki
    fun getAllCalls(): List<Call> {
        return queue.toList()
    }

    fun getCallsByProductType(productType: ProductType): List<Call> {
        return queue.filter { it.product.type == productType }
    }

    // Remove and return the first call from the queue.
    fun dequeue(): Call? {
        return queue.poll()
    }

    // Peek at the first call in the queue without removing it.
    fun peek(): Call? {
        return queue.peek()
    }

    // Check if the queue is empty.
    fun isEmpty(): Boolean {
        return queue.isEmpty()
    }

    // Clear all calls from the queue.
    fun clear() {
        queue.clear()
    }

    // Get the current size of the queue.
    fun size(): Int {
        return queue.size
    }
}
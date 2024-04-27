package uk.ac.aber.mycookshop.ui.Production.model

import uk.ac.aber.mycookshop.model.Section
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

    // Usuwa wybrany call i zwraca true jezeli sie udalo i false jezeli nie odnalazl takiego calla
    fun removeCall(call: Call): Boolean {
        return queue.remove(call)
    }

    fun removeByProductType (type: ProductType): Call? {
        val oldestCall = queue.firstOrNull { it.product.type == type }

        if(oldestCall != null) {
            queue.remove(oldestCall)
        }
        return oldestCall
    }

        fun getOldestCallByType(type: ProductType): Call {

            val oldestCall = queue.first { it.product.type == type }
        return oldestCall ?: Call(-1, ProductModel("",0,0,0.0,0.0,
            ProductType.BACON, Section.FRONT,0,0,0, 0, 0),0, ProductStatus.ERROR
        )
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
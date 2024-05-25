package uk.ac.aber.mycookshop.ui.elements.orders.model

import uk.ac.aber.mycookshop.ui.elements.Production.model.ProductList
import uk.ac.aber.mycookshop.ui.elements.Production.model.ProductType

class OrderItem(
    val name: String,
    val product: ProductType,
    var amount: Int,
    var price: Double
) {
    companion object {
        private var nextId = 1

        private fun generateNextId(): Int {
            return nextId++
        }
    }

    constructor(
        name: String,
        product: ProductType,
        amount: Int
    ) : this(name, product, amount, 0.0) {
        val model = ProductList.productList.find { it.type == product }
        model?.let {
            this.price = it.price * amount
        }
    }
}
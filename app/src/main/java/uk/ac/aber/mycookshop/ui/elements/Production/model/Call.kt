package uk.ac.aber.mycookshop.ui.elements.Production.model

class Call(
    val id: Int,
    val product: ProductModel,
    var amount: Int,
    var status: ProductStatus
) {
    companion object {
        private var nextId = 0

        private fun generateNextId(): Int {
            return nextId++
        }
    }
    constructor(
        product: ProductModel,
        ilosc: Int,
        status: ProductStatus
    ) : this(generateNextId(), product, ilosc, status)

}

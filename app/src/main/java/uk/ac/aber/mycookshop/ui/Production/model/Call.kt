package uk.ac.aber.mycookshop.ui.Production.model

class Call(
    val id: Int,
//    val time: Long,           nie wiem jak dodac
    val product: ProductModel,
    var ilosc: Int,
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

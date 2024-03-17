package uk.ac.aber.mycookshop.model

import uk.ac.aber.mycookshop.hardcodedData.ProductModel

class Call(
    val id: Int,
//    val time: Long,           nie wiem jak dodac
    val product: ProductModel,
    val ilosc: Int,
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

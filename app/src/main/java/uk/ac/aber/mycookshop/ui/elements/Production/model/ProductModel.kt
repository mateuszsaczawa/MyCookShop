package uk.ac.aber.mycookshop.ui.elements.Production.model

data class ProductModel(
    val name: String,
    val cookingTimeInSeconds: Int,
    val expiryTimeInSeconds: Int,
    val cost: Double,
    val price: Double,
    val type: ProductType,
    val section: Section,
    var total: Int = 0,
    var onHand: Int = 0,
    var waste: Int = 0,
    var sold: Int = 0,
    val image: Int
)

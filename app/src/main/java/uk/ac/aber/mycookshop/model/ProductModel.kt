package uk.ac.aber.mycookshop.model

import uk.ac.aber.mycookshop.model.ProductType
import uk.ac.aber.mycookshop.model.Section

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

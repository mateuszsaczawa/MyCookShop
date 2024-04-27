package uk.ac.aber.mycookshop.ui.orders.model

import uk.ac.aber.mycookshop.ui.Production.model.ProductType

object MealList {

    val combos = listOf(
        OrderItem("Chicken Pieces", ProductType.CHICKEN_ON_THE_BONE, 5),
        OrderItem("Chicken Pieces", ProductType.CHICKEN_ON_THE_BONE, 12),
        OrderItem("Chicken Pieces", ProductType.CHICKEN_ON_THE_BONE, 20),
        OrderItem("Hotwings", ProductType.HOTWING, 6),
        OrderItem("Hotwings", ProductType.HOTWING, 14),
        OrderItem("Hotwings", ProductType.HOTWING, 25),
        OrderItem("Mini Fillets", ProductType.MINI_FILLET, 4),
        OrderItem("Mini Fillets", ProductType.MINI_FILLET, 9),
        OrderItem("Mini Fillets", ProductType.MINI_FILLET, 16),
//        listOf(OrderItem(ProductType.CHICKEN_ON_THE_BONE, 5),OrderItem(ProductType.HOTWING, 6))
    )


    fun getSize(): Int {
        return combos.size
    }

    fun getOrder(num: Int): OrderItem {
        return combos[num]
    }
}
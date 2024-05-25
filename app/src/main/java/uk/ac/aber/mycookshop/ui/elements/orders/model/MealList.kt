package uk.ac.aber.mycookshop.ui.elements.orders.model

import uk.ac.aber.mycookshop.ui.elements.Production.model.ProductType

object MealList {

    val combos = listOf(
        OrderItem("Chicken Feast", ProductType.CHICKEN_ON_THE_BONE, 8),
        OrderItem("Wing Party", ProductType.HOTWING, 20),
        OrderItem("Fillet Duo", ProductType.FILLET, 2),
        OrderItem("Zinger Rush", ProductType.ZINGER, 3),
        OrderItem("Popcorn Bucket", ProductType.POPCORN, 1),
        OrderItem("Bite Snack", ProductType.BITES, 15),
        OrderItem("French Fries Large", ProductType.FRIES, 3),
        OrderItem("Bacon Crunch", ProductType.BACON, 10),
        OrderItem("Beans Pot", ProductType.BEANS, 2),
        OrderItem("Corn on the Cob", ProductType.CORN, 4),
        OrderItem("Hashbrown Munch", ProductType.HASHBROWN, 8),
        OrderItem("Cookie Delight", ProductType.COOKIE, 6),
        OrderItem("Muffin Mix", ProductType.MUFFIN, 5),
        OrderItem("Ice Cream Scoops", ProductType.ICE_CREAM, 3),
        OrderItem("Mixed Bucket", ProductType.CHICKEN_ON_THE_BONE, 10),
        OrderItem("Family Feast", ProductType.CHICKEN_ON_THE_BONE, 15),
        OrderItem("Spicy Wings", ProductType.HOTWING, 30),
        OrderItem("Mini Fillet Meal", ProductType.MINI_FILLET, 10),
        OrderItem("Fillet Feast", ProductType.FILLET, 6),
        OrderItem("Zinger Box", ProductType.ZINGER, 4),
        OrderItem("Popcorn Share", ProductType.POPCORN, 2),
        OrderItem("Snack Bites", ProductType.BITES, 20),
        OrderItem("Fries Party Pack", ProductType.FRIES, 5),
        OrderItem("Bacon Feast", ProductType.BACON, 8),
        OrderItem("Beans Family Size", ProductType.BEANS, 5),
        OrderItem("Corn Fiesta", ProductType.CORN, 6),
        OrderItem("Hashbrown Party", ProductType.HASHBROWN, 12),
        OrderItem("Sweet Cookie Batch", ProductType.COOKIE, 12),
        OrderItem("Muffin Selection", ProductType.MUFFIN, 10),
        OrderItem("Ice Cream Party", ProductType.ICE_CREAM, 5),
        OrderItem("Ultimate Chicken Mix", ProductType.CHICKEN_ON_THE_BONE, 20),
        OrderItem("Wing Lover's Pack", ProductType.HOTWING, 50),
        OrderItem("Fillet Deluxe", ProductType.FILLET, 4),
        OrderItem("Zinger Mega Box", ProductType.ZINGER, 5),
        OrderItem("Popcorn Mega Pack", ProductType.POPCORN, 3),
        OrderItem("Bite Feast", ProductType.BITES, 30),
        OrderItem("Super Fries Bucket", ProductType.FRIES, 4),
        OrderItem("Bacon Deluxe", ProductType.BACON, 12),
        OrderItem("Beans Deluxe", ProductType.BEANS, 3),
        OrderItem("Corn Deluxe", ProductType.CORN, 5)
    )



    fun getSize(): Int {
        return combos.size
    }

    fun getOrder(num: Int): OrderItem {
        return combos[num]
    }
}
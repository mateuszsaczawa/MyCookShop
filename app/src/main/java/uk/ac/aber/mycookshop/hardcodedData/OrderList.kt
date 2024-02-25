package uk.ac.aber.mycookshop.hardcodedData

data class Order(
    val cost: String,
    val id: String,
    val items: List<String>
)

object OrderList {
    val orders = listOf(
        Order("10 PLN", "1", listOf("Pizza", "Cola")),
        Order("15 PLN", "2", listOf("Burger", "Fries")),
        Order("8 PLN", "3", listOf("Salad")),
        Order("20 PLN", "4", listOf("Steak", "Wine")),
        Order("6 PLN", "5", listOf("Ice Cream")),
        Order("12 PLN", "6", listOf("Spaghetti", "Soda")),
        Order("14 PLN", "7", listOf("Sushi", "Green Tea")),
        Order("16 PLN", "8", listOf("Fish and Chips")),
        Order("11 PLN", "9", listOf("Sandwich", "Juice")),
        Order("9 PLN", "10", listOf("Soup", "Bread"))
    )
}
package uk.ac.aber.mycookshop.hardcodedData

data class Order(
    val cost: String,
    val id: String,
    val items: List<String>
)

object OrderList {
    val orders = listOf(
        Order("10", "1", listOf("4 pieces of Chicken", "Zinger burger","4 pieces of Chicken", "Zinger burger","4 pieces of Chicken", "Zinger burger","4 pieces of Chicken", "Zinger burger","4 pieces of Chicken", "Zinger burger")),
        Order("15", "2", listOf("Fillet Burger", "Fries")),
        Order("8", "3", listOf("24 Mini-fillets")),
        Order("20", "4", listOf("Ice cream", "Cookie")),
        Order("6", "5", listOf("Ice Cream")),
        Order("12", "6", listOf("Spaghetti", "Soda")),
        Order("14", "7", listOf("Sushi", "Green Tea")),
        Order("16", "8", listOf("Fish and Chips")),
        Order("11", "9", listOf("Sandwich", "Juice")),
        Order("9", "10", listOf("Soup", "Bread"))
    )
}
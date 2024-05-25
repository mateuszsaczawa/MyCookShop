package uk.ac.aber.mycookshop.ui.elements.navigation


sealed class Navig (val route: String) {
    object Orders: Navig("orders")
    object Cook : Navig("cook")
    object Mid : Navig("mid")
    object Front : Navig("front")

}

val screens = listOf(
    Navig.Orders,
    Navig.Cook,
    Navig.Mid,
    Navig.Front
)
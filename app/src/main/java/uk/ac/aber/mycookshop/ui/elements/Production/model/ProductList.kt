package uk.ac.aber.mycookshop.ui.elements.Production.model

import uk.ac.aber.mycookshop.R

object ProductList {
    val productList = listOf(
        ProductModel("Chicken", 20*60, 90*60,0.01, 3.2, ProductType.CHICKEN_ON_THE_BONE, Section.COOK,0,0,0,0, R.drawable.kurczak1),
        ProductModel("Wings", 7*60, 90*60,0.01, 1.2, ProductType.HOTWING, Section.COOK,0,0, 0,0, R.drawable.kurczak1),
        ProductModel("Strips", 4*60, 40*60,0.01, 2.0, ProductType.MINI_FILLET, Section.COOK,0,0,0,0, R.drawable.kurczak1),
        ProductModel("Filets", 5*60, 60*60,0.01, 1.2, ProductType.FILLET, Section.COOK,0,0,0,0, R.drawable.kurczak1),
        ProductModel("Zingers", 5*60, 60*60,0.01, 1.2, ProductType.ZINGER, Section.COOK,0,0,0,0, R.drawable.kurczak1),
        ProductModel("Popcorn", 7*60, 120*60,0.01, 1.2, ProductType.POPCORN, Section.COOK,0,0,0,0, R.drawable.kurczak1),
        ProductModel("Bites", 3*60, 120*60,0.01, 1.2, ProductType.BITES, Section.COOK,0,0,0,0, R.drawable.kurczak1),

        ProductModel("Fries", 3*60, 10*60,0.01, 1.2, ProductType.FRIES, Section.COOK,0,0,0,0, R.drawable.kurczak1),
        ProductModel("Bacon", 20*60, 300*60,0.01, 1.2, ProductType.BACON, Section.COOK,0,0,0,0, R.drawable.kurczak1),
        ProductModel("Beans", 30*60, 500*60,0.01, 1.2, ProductType.BEANS, Section.COOK,0,0,0,0, R.drawable.kurczak1),
        ProductModel("Corn", 60*60, 500*60,0.01, 1.2, ProductType.CORN, Section.COOK,0,0,0,0, R.drawable.kurczak1),
        ProductModel("Hash", 4*60, 60*60,0.01, 1.2, ProductType.HASHBROWN, Section.COOK,0,0,0,0, R.drawable.kurczak1),

        ProductModel("Cookies", 30*60, 800*60,0.01, 1.2, ProductType.COOKIE, Section.COOK,0,0,0,0, R.drawable.kurczak1),
        ProductModel("Muffins", 120*60, 800*60,0.01, 1.2, ProductType.MUFFIN, Section.COOK,0,0,0,0, R.drawable.kurczak1),
        ProductModel("Ice cream", 1*60, 800*60,0.01, 1.2, ProductType.ICE_CREAM, Section.COOK,0,0,0,0, R.drawable.kurczak1),
    )
}
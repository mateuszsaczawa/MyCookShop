package uk.ac.aber.mycookshop.hardcodedData

import uk.ac.aber.mycookshop.R
import uk.ac.aber.mycookshop.model.ProductType
import uk.ac.aber.mycookshop.model.Section

object ProductList {
    val productList = listOf(
        ProductModel("Chicken", 20*60, 0.01, 1.2, ProductType.CHICKEN_ON_THE_BONE, Section.COOK,0,0,0, R.drawable.kurczak1),
        ProductModel("Wings", 20*60, 0.01, 1.2, ProductType.HOTWING, Section.COOK,0,0,0, R.drawable.kurczak1),
        ProductModel("Strips", 20*60, 0.01, 1.2, ProductType.MINI_FILLET, Section.COOK,0,0,0, R.drawable.kurczak1),
        ProductModel("Filets", 20*60, 0.01, 1.2, ProductType.FILLET, Section.COOK,0,0,0, R.drawable.kurczak1),
        ProductModel("Zingers", 20*60, 0.01, 1.2, ProductType.ZINGER, Section.COOK,0,0,0, R.drawable.kurczak1),
        ProductModel("Popcorn", 20*60, 0.01, 1.2, ProductType.POPCORN, Section.COOK,0,0,0, R.drawable.kurczak1),
        ProductModel("Bites", 20*60, 0.01, 1.2, ProductType.BITES, Section.COOK,0,0,0, R.drawable.kurczak1),

        ProductModel("Fries", 20*60, 0.01, 1.2, ProductType.FRIES, Section.COOK,0,0,0, R.drawable.kurczak1),
        ProductModel("Bacon", 20*60, 0.01, 1.2, ProductType.BACON, Section.COOK,0,0,0, R.drawable.kurczak1),
        ProductModel("Beans", 20*60, 0.01, 1.2, ProductType.BEANS, Section.COOK,0,0,0, R.drawable.kurczak1),
        ProductModel("Corn", 20*60, 0.01, 1.2, ProductType.CORN, Section.COOK,0,0,0, R.drawable.kurczak1),
        ProductModel("Hash", 20*60, 0.01, 1.2, ProductType.HASHBROWN, Section.COOK,0,0,0, R.drawable.kurczak1),

        ProductModel("Cookies", 20*60, 0.01, 1.2, ProductType.COOKIE, Section.COOK,0,0,0, R.drawable.kurczak1),
        ProductModel("Muffins", 20*60, 0.01, 1.2, ProductType.MUFFIN, Section.COOK,0,0,0, R.drawable.kurczak1),
        ProductModel("Ice cream", 20*60, 0.01, 1.2, ProductType.ICE_CREAM, Section.COOK,0,0,0, R.drawable.kurczak1),
    )
}
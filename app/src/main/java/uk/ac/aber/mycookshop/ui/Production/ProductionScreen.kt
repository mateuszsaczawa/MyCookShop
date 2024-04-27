package uk.ac.aber.mycookshop.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import uk.ac.aber.mycookshop.clock.GameClockComposable
import uk.ac.aber.mycookshop.hardcodedData.ProductList.productList
import uk.ac.aber.mycookshop.ui.Production.model.ProductModel
import uk.ac.aber.mycookshop.ui.navigation.TopLevelScaffold
import uk.ac.aber.mycookshop.ui.Production.ProductRows
import uk.ac.aber.mycookshop.ui.Production.WasteBoard
import uk.ac.aber.mycookshop.ui.firstCook.FirstCookAlertDialog
import uk.ac.aber.mycookshop.ui.orders.OrderBar
import uk.ac.aber.mycookshop.viewModel.ProductionViewModel

@Composable
fun ProductionScreen(
    navController: NavHostController,
    productionViewModel: ProductionViewModel,
    name : String
) {
    val coroutineScope = rememberCoroutineScope()

    val isAlertOpen = productionViewModel.isAlertDialogOpen.collectAsState().value

    val subList: List<ProductModel> = when {
        name == "cook" -> productList.subList(0, 7)
        name == "mid" -> productList.subList(7, 12)
        name == "front" -> productList.subList(12, productList.size)
        else -> emptyList() // Default list if the name doesn't match any case.
    }



    TopLevelScaffold(
        navController = navController,
        coroutineScope = coroutineScope,
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 25.dp, top = 60.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(1.dp)
                    .padding(bottom = 10.dp)
            ) {
                GameClockComposable(productionViewModel)
                WasteBoard(productionViewModel)
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.8f)
                        .padding(4.dp)
                ) {

                    ProductRows(productionViewModel, subList)
                }
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(start = 6.dp, end = 6.dp)
//                        .border(width = 2.dp, color = Color.Black)
//                        .padding(6.dp)
                ) {
                    OrderBar(productionViewModel)
                }
            }
        }
        if (isAlertOpen) {
            FirstCookAlertDialog(productionViewModel)
        }
    }
}
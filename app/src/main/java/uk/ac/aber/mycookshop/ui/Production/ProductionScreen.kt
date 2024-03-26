package uk.ac.aber.mycookshop.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import uk.ac.aber.mycookshop.clock.Clock
import uk.ac.aber.mycookshop.hardcodedData.OrderList
import uk.ac.aber.mycookshop.hardcodedData.ProductList.productList
import uk.ac.aber.mycookshop.model.ProductModel
import uk.ac.aber.mycookshop.ui.navigation.TopLevelScaffold
import uk.ac.aber.mycookshop.ui.Production.ProductRows
import uk.ac.aber.mycookshop.ui.Production.WasteBoard
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
        else -> emptyList() // Domyślna lista, jeśli name nie pasuje do żadnego przypadku
    }

    TopLevelScaffold(
        navController = navController,
        coroutineScope = coroutineScope,
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(bottom = 25.dp, top = 60.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(1.dp)
            ) {
                Clock(productionViewModel)
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.8f)
                        .padding(6.dp)
                ) {
                    WasteBoard()
                    ProductRows(productionViewModel, subList)
                }
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(start = 6.dp, end = 6.dp)
//                        .border(width = 2.dp, color = Color.Black)
//                        .padding(6.dp)
                ) {
                    OrderBar(OrderList.orders)
                }
            }
        }
        if (isAlertOpen) {
            AlertDialog(
                onDismissRequest = { productionViewModel.setAlertDialogOpen(false) },
                title = { Text("New day") },
                text = { Text("Congratulation you finish day " + (productionViewModel.gameTime.value.getDay()-1)) },
                confirmButton = {
                    Button(
                        onClick = { productionViewModel.setAlertDialogOpen(false) }
                    ) {
                        Text("Start next day")
                    }
                }
            )
        }
    }
}
package uk.ac.aber.mycookshop.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import uk.ac.aber.mycookshop.hardcodedData.ProductList.productList
import uk.ac.aber.mycookshop.hardcodedData.ProductModel
import uk.ac.aber.mycookshop.ui.navigation.TopLevelScaffold
import uk.ac.aber.mycookshop.ui.screens.elements.Production.ProductRows
import uk.ac.aber.mycookshop.ui.screens.elements.Production.WasteBoard
import uk.ac.aber.mycookshop.ui.screens.elements.clock.GameClock
import uk.ac.aber.mycookshop.viewModel.ProductionViewModel

@Composable
fun ProductionScreen(
    navController: NavHostController,
    productionViewModel: ProductionViewModel,
    name : String
) {
    val coroutineScope = rememberCoroutineScope()

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
                GameClock()
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.8f)
                        .padding(6.dp)
                ){
                    WasteBoard()
                    ProductRows(productionViewModel, subList)
                }
            }
        }
    }
}
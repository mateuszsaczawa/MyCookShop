package uk.ac.aber.mycookshop.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import uk.ac.aber.mycookshop.clock.GameClockComposable
import uk.ac.aber.mycookshop.ui.navigation.TopLevelScaffold
import uk.ac.aber.mycookshop.ui.Production.WasteBoard
import uk.ac.aber.mycookshop.ui.screens.elements.orders.OrderScrollableColumn
import uk.ac.aber.mycookshop.viewModel.ProductionViewModel


@Composable
fun OrderScreen(
    productionViewModel: ProductionViewModel,
    navController: NavHostController
) {

    val coroutineScope = rememberCoroutineScope()

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
                    .fillMaxSize()
                    .padding(4.dp)

            ) {
                GameClockComposable(productionViewModel)
                WasteBoard(productionViewModel)
                OrderScrollableColumn(productionViewModel)
            }

        }
    }
}

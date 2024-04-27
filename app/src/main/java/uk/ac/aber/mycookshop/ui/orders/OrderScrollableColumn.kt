package uk.ac.aber.mycookshop.ui.screens.elements.orders

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.ac.aber.mycookshop.viewModel.ProductionViewModel
import androidx.compose.runtime.*
import uk.ac.aber.mycookshop.ui.orders.model.OrderQueue
import uk.ac.aber.mycookshop.ui.orders.model.OrderStatus


@Composable
fun OrderScrollableColumn(
    productionViewModel: ProductionViewModel
) {

    val selectedTab by productionViewModel.selectedTab.collectAsState()

    val pendingOrder = OrderQueue.getOrdersWithStatus(OrderStatus.PENDING)
    val servicedOrder = OrderQueue.getOrdersWithStatus(OrderStatus.SERVICED)

    Column {
        TabRow(selectedTabIndex = selectedTab) {
            Tab(
               selected = selectedTab == 0,
               onClick = { productionViewModel.setSelectedTab(0) },
               text = { Text("Pending") }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { productionViewModel.setSelectedTab(1) },
                text = { Text("Serviced") }
            )
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {

        if(selectedTab == 0){
            pendingOrder.forEach { order ->
                item {
                    OrderCardMax(productionViewModel, order)
                }
            }
        } else {
            servicedOrder.forEach() { order ->
                item {
                    OrderCardMax(productionViewModel, order)
                }
            }
        }
    }
}




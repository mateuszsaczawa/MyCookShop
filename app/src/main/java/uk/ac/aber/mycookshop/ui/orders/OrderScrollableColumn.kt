package uk.ac.aber.mycookshop.ui.screens.elements.orders

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.ac.aber.mycookshop.model.order.Order
import uk.ac.aber.mycookshop.viewModel.ProductionViewModel
import androidx.compose.runtime.*


@Composable
fun OrderScrollableColumn(
    productionViewModel: ProductionViewModel
) {

    val orderList by productionViewModel.orderList.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        items(orderList.size) { index ->

            OrderCardMax(productionViewModel, orderList[index])
        }
    }
}




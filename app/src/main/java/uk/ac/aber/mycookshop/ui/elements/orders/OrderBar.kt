package uk.ac.aber.mycookshop.ui.elements.orders

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.ac.aber.mycookshop.viewModel.ProductionViewModel

@Composable
fun OrderBar(productionViewModel: ProductionViewModel) {

    val orderList by productionViewModel.orderList.collectAsState()
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f),
        horizontalArrangement = Arrangement.Start
    ) {
        items(orderList.size) { index ->
            Spacer(modifier = Modifier.width(4.dp))
            OrderCardMin(orderList[index], productionViewModel)
        }
    }
}
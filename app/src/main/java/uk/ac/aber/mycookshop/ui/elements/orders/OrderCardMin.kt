package uk.ac.aber.mycookshop.ui.elements.orders

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import uk.ac.aber.mycookshop.ui.elements.orders.model.Order
import uk.ac.aber.mycookshop.ui.elements.orders.model.OrderStatus
import uk.ac.aber.mycookshop.viewModel.ProductionViewModel

@Composable
fun OrderCardMin(
    order: Order,
    productionViewModel: ProductionViewModel
) {
    if (order.status == OrderStatus.PENDING) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clickable {
                    productionViewModel.sellProductsFromOrder(order)
                }
                .border(2.dp, Color.Black, RoundedCornerShape(10.dp))
                .padding(3.dp),

            ) {
            LazyColumn(
                modifier = Modifier
                    .padding(3.dp)
            ) {
                order.products.forEach { item ->
                    item {
                        if (item.amount != 0) {
                            Text(
                                text = "x${item.amount} ${item.name}",
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

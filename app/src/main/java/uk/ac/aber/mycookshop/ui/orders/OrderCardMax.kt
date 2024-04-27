package uk.ac.aber.mycookshop.ui.screens.elements.orders

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.ac.aber.mycookshop.ui.orders.model.Order
import uk.ac.aber.mycookshop.ui.Production.secondsToTimer
import uk.ac.aber.mycookshop.viewModel.ProductionViewModel

@Composable
fun OrderCardMax(
    productionViewModel: ProductionViewModel,
    order: Order
) {

    val orderTime = productionViewModel.orderTimeMap[order]?.collectAsState()

//    val isServiced by productionViewModel.isOrderServiced[order.id]?.collectAsState(initial = order.isServiced) ?: mutableStateOf(false)


        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable {
                           productionViewModel.sellProductsFromOrder(order)
                },
            shape = RoundedCornerShape(8.dp)
        ) {
//            if(isServiced){
//                Text(text = "Packing")
//                println("cipkaaa packing")
//            } else {
//                println("cipka " + isServiced + order.id)
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "\u00A3${"%.2f".format(order.cost)}")
                    Text(text = "#: ${order.id}")
                    Text(text = secondsToTimer(orderTime?.value))
                }

                Spacer(modifier = Modifier.height(8.dp))  // Odstęp między wierszami

                Text(text = "Order:")
                order.products.forEach { item ->
                    if (item.amount != 0) {
                        Text(text = "- x${item.amount} ${item.name}", modifier = Modifier.padding(top = 4.dp))
                    }
            }
        }
    }
}

package uk.ac.aber.mycookshop.ui.orders

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import uk.ac.aber.mycookshop.ui.orders.model.Order

@Composable
fun OrderCardMin(order: Order) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
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
                        Text(text = "x${item.amount} ${item.name}", modifier = Modifier.padding(top = 4.dp))
                    }
                }


            }
//            order.items.forEach { index ->
//                item {
//                    Text(
//                        text = index,
//                        modifier = Modifier
//                            .padding(top = 1.dp),
//                        fontSize = 12.sp
//                    )
//                }
//
//
//            }
        }
    }
}

@Composable
fun OrderCardContent(item : String)
{

}
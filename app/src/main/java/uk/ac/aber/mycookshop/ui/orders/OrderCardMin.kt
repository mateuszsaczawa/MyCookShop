package uk.ac.aber.mycookshop.ui.orders

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.aber.mycookshop.hardcodedData.Order

@Composable
fun OrderCardMin(order: Order) {
    Card(
        modifier = Modifier
            .padding(1.dp)
            .fillMaxWidth(0.33f)
            .fillMaxHeight(),
        shape = RoundedCornerShape(8.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(8.dp)
        ) {
            order.items.forEach { index ->
                item {
                    Text(
                        text = "- $index",
                        modifier = Modifier
                            .padding(top = 1.dp),
                        fontSize = 12.sp
                    )
                }

                
            }
        }
    }
}

@Composable
fun OrderCardContent(item : String)
{

}
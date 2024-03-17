package uk.ac.aber.mycookshop.ui.orders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.ac.aber.mycookshop.hardcodedData.Order

@Composable
fun OrderBar(orders: List<Order>) {
    var isExpanded by remember { mutableStateOf(false) }
    var number by remember { mutableStateOf("") }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f),
        horizontalArrangement = Arrangement.Start
    ) {
        orders.forEach { index ->
            item {
                OrderCardMin(index)
            }



//        item {
//            LazyRow(
//                horizontalArrangement = Arrangement.SpaceEvenly,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 16.dp) // Dodaj padding od dołu
//
//            ) {
//                for (i in 0 until 5) {
//                    ProductCard()
//                }
//            }
//            Spacer(modifier = Modifier.fillMaxSize())
//        }


        }
    }
}
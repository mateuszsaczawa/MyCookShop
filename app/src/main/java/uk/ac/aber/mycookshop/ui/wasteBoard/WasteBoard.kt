package uk.ac.aber.mycookshop.ui.Production

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import uk.ac.aber.mycookshop.hardcodedData.ProductList.productList
import uk.ac.aber.mycookshop.model.BottomBorderShape
import uk.ac.aber.mycookshop.model.TopBorderShape
import uk.ac.aber.mycookshop.viewModel.ProductionViewModel


@Composable
fun WasteBoard(
    productionViewModel: ProductionViewModel
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(3.dp, color = Color.Black)
            .padding(6.dp)
            .padding(start = 2.dp)
    ) {
        // Pierwszy rząd z 5 pierwszymi elementami
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Waste Board")
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 3.dp) // Dodaj padding od dołu
//                    .padding(bottom = 16.dp) // Dodaj padding od dołu


        ) {
            for (i in 0 until 5) {
                CircleItem(productionViewModel, productList[i])
            }

            // Przycisk rozwijania listy
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .fillMaxWidth()
                    .weight(1f)
                    .clickable { isExpanded = !isExpanded }
            ) {
                if (isExpanded) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center) // Zastosuj scale jeśli to konieczne
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)// Zastosuj scale jeśli to konieczne
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))


        // Jeśli lista jest rozwinięta, wyświetl pozostałe elementy
        if (isExpanded) {
            val remainingItems1 = productList.subList(5, 10)

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()

                    .background(
                        color = Color.Black,
                        shape = TopBorderShape,
                    )
                    .background(
                        color = Color.Black,
                        shape = BottomBorderShape,
                    )
                    .padding(top = 8.dp) // Dodaj padding od dołu

            ) {
                remainingItems1.forEach { rowItems ->
                    CircleItem(productionViewModel, rowItems)
                }
            }


            val remainingItems2 = productList.subList(10, productList.size)

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp) // Dodaj padding od dołu

            ) {
                remainingItems2.forEach { rowItems ->
                    CircleItem(productionViewModel, rowItems)

                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }

    }
}
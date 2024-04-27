package uk.ac.aber.mycookshop.ui.firstCook


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.Dialog
import uk.ac.aber.mycookshop.hardcodedData.ProductList
import uk.ac.aber.mycookshop.ui.Production.ProductRows
import uk.ac.aber.mycookshop.viewModel.ProductionViewModel

@Composable
fun FirstCookAlertDialog(productionViewModel: ProductionViewModel) {

    val tabNames = listOf("Cook", "Mid", "Front")

    val selectedTabIndex = remember { mutableStateOf(0) }


    fun onNextTab() {
        if (selectedTabIndex.value < tabNames.size - 1) {
            selectedTabIndex.value += 1 // Move to the next tab
        } else {
            productionViewModel.setAlertDialogOpen(false) // Close the dialog or handle the final action
        }
    }

    Dialog(onDismissRequest = {  }) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
//                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
//                    .verticalScroll(rememberScrollState())
                ) {
                    Text("Witaj w nowym dniu!", style = MaterialTheme.typography.bodyLarge)

                    TabRow(selectedTabIndex = selectedTabIndex.value) {
                        tabNames.forEachIndexed { index, title ->
                            Tab(
                                selected = (selectedTabIndex.value == index),
                                onClick = { selectedTabIndex.value = index },
                                text = { Text(title) }
                            )
                        }
                    }

                    when (selectedTabIndex.value) {
                        0 -> ProductsFirstCook(productionViewModel, ProductList.productList.subList(0, 7), onNextTab = { onNextTab() })
                        1 -> ProductsFirstCook(productionViewModel, ProductList.productList.subList(7, 12), onNextTab = { onNextTab() })
                        2 -> ProductsFirstCook(productionViewModel, ProductList.productList.subList(12, ProductList.productList.size), onNextTab = { onNextTab() })
                    }
            }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                ){
                    Button(

                        onClick = {
                            productionViewModel.loadPreviousOrDefultFirstCook()
                            productionViewModel.setAlertDialogOpen(false)
                        }
                    ) {
                        Text("Previous First Cook")
                    }

                    Button(
                        onClick = {
                            if (selectedTabIndex.value < 2) {
                                selectedTabIndex.value = selectedTabIndex.value + 1
                            } else {
                                productionViewModel.setAlertDialogOpen(false)
                                productionViewModel.doFirstCook()
                            }
                        },
                        modifier = Modifier
//                            .padding(top = 16.dp)
                    ) {
                        Text(if (selectedTabIndex.value < 2) "Next" else "Start day")
                    }
                }
            }
        }
    }
}


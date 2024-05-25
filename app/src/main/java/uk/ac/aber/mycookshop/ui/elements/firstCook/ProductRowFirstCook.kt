package uk.ac.aber.mycookshop.ui.elements.firstCook

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.aber.mycookshop.ui.elements.Production.model.ProductModel
import uk.ac.aber.mycookshop.ui.theme.light_productionBox
import uk.ac.aber.mycookshop.viewModel.ProductionViewModel

@Composable
fun ProductsFirstCook(
    productionViewModel: ProductionViewModel,
    items: List<ProductModel>,
    onNextTab: () -> Unit // Callback to trigger when last item's next is pressed
) {
    val focusManager = LocalFocusManager.current
    val lastIndex = items.size - 1

    LazyColumn(
        modifier = Modifier.padding(top = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(items) { index, product ->
            val callAmount = remember { mutableStateOf(productionViewModel.productsFirstCook[product] ?: 0) }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 3.dp, color = Color.Black, shape = RoundedCornerShape(10.dp))
                    .background(color = light_productionBox, shape = RoundedCornerShape(10.dp))
                    .padding(end = 2.dp, top = 8.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,

            ) {

                Spacer(modifier = Modifier.weight(0.02f))

                Text(
                    modifier = Modifier
                        .weight(0.2f),
                    text = product.name
                )
                Spacer(modifier = Modifier.weight(0.02f))

                TextField(
                    value = if (callAmount.value == 0) "" else callAmount.value.toString(),
                    onValueChange = { value ->
                        val newValue = value.filter { it.isDigit() }.take(2)
                        val newAmount = newValue.toIntOrNull() ?: 0
                        callAmount.value = newAmount
                        productionViewModel.updateProductFirstCook(product, newAmount)
                    },
                    singleLine = true,
                    modifier = Modifier
                        .width(80.dp)
                        .height(50.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = if (index == lastIndex) ImeAction.Done else ImeAction.Next
                    ),
                    textStyle = TextStyle(fontSize = 12.sp, textAlign = TextAlign.Center),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            if (index != lastIndex) focusManager.moveFocus(FocusDirection.Next)
                            else focusManager.clearFocus()
                        },
                        onDone = {
                            focusManager.clearFocus()
                            if (index == lastIndex) onNextTab() // If last item, call the next tab function
                        }

                    )
                )
                Spacer(modifier = Modifier.weight(0.02f))
            }
        }
    }
}
//
//
//            TextField(
//                value = if (callAmount == 0) "" else callAmount.toString(),
//                onValueChange = { value ->
//                    val newValue = value.filter { it.isDigit() }.take(2) // Ograniczenie do dwóch cyfr
//                    callAmount = newValue.toIntOrNull() ?: 0
//                },
//                singleLine = true,
//                modifier = Modifier
//                    .width(80.dp)
//                    .height(50.dp),
//                keyboardOptions = KeyboardOptions(
//                    keyboardType = KeyboardType.Number,
//                    imeAction = if (index == lastIndex) ImeAction.Done else ImeAction.Next
//                ),
//                textStyle = TextStyle(fontSize = 12.sp, textAlign = TextAlign.Center),
//                shape = RoundedCornerShape(10.dp),
//                keyboardActions = KeyboardActions(
//                    onNext = {
//                        if (index != lastIndex) focusManager.moveFocus(FocusDirection.Next)
//                        else focusManager.clearFocus()
//                    }
//                    onDone = {
//                        productionViewModel.addNewCall(product, callAmount)
//                        focusManager.clearFocus()
//                        if(index == lastIndex) onNextTab()
//
//                    },
//                )
//            )
//
//
//        }
//    }
//}
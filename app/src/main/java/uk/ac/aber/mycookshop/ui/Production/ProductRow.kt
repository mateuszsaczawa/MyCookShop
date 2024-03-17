package uk.ac.aber.mycookshop.ui.Production

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.aber.mycookshop.hardcodedData.ProductModel
import uk.ac.aber.mycookshop.ui.theme.AutoResizedText
import uk.ac.aber.mycookshop.ui.theme.light_productionBox
import uk.ac.aber.mycookshop.viewModel.ProductionViewModel


@Composable
fun ProductRow(
    productionViewModel: ProductionViewModel,
    product: ProductModel
) {
    var isExpanded by remember { mutableStateOf(false) }
    var callAmount by remember { mutableStateOf("") }


    Column(modifier = Modifier
        .fillMaxWidth()
        .border(width = 3.dp, color = Color.Black, shape = RoundedCornerShape(10.dp))
        .background(color = light_productionBox, shape = RoundedCornerShape(10.dp))
        .padding( end = 2.dp, top = 8.dp, bottom = 8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .weight(0.3f)
                    .padding(start = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                AutoResizedText(
                    modifier = Modifier,
                    text = product.name
                )
//                Spacer(modifier = Modifier.width(5.dp))
                AutoResizedText(
                    text = ": 0"
                )
            }
            Spacer(modifier = Modifier.weight(0.05f))
            Column(
                modifier = Modifier.weight(0.2f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = callAmount,
                    onValueChange = { value ->
                        if (value.length <= 2) {
                            callAmount = value.filter { it.isDigit() }
                        }
                    },
                    modifier = Modifier
                        .width(80.dp)
                        .height(50.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    textStyle = TextStyle(fontSize = 12.sp, textAlign = TextAlign.Center),
                    shape = RoundedCornerShape(10.dp),
                    placeholder = {
                        Text(
                        modifier = Modifier,
                        text = "To cook: 0",
                        fontSize = 8.sp
                    )
                    },
                    keyboardActions = KeyboardActions(
                        onDone = {
                        },

                        )
                )
            }
            Spacer(modifier = Modifier.weight(0.05f))
            Box(
                modifier = Modifier.weight(0.2f),
            ) {
                Text(
                    text = "Cook",
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier
                        .background(Color.Black, shape = RoundedCornerShape(10.dp))
                        .clickable { }
                        .height(50.dp)
                        .width(70.dp)
                        .wrapContentHeight(),
                    textAlign = TextAlign.Center


                )
            }
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded }
                    .weight(0.1f)
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
        if (isExpanded)
        {
            Spacer(modifier = Modifier.height(5.dp))
            Divider(modifier = Modifier.fillMaxWidth(), thickness = 3.dp, color = Color.Black)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(modifier = Modifier) {
                    Text(
                        text = "Sold:",
                        color = Color.Green
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "0",
                        color = Color.Green)
                }
                Row(modifier = Modifier) {
                    Text(
                        text = "Waste:",
                        color = Color.Red
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "0",
                        color = Color.Red
                    )
                }
                Row(modifier = Modifier) {
                    Text(text = "Total:")
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = "0")
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = "Amount")
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "Status")
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "Expiry Time")
            }
            Divider(
                modifier = Modifier
                    .height(2.dp)
                    .fillMaxWidth(0.9f)
                    .align(Alignment.CenterHorizontally),
                color = Color.Black,
            )
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = "20")
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "15:24")
            }
        }
    }
    Spacer(modifier = Modifier.height(5.dp))
}
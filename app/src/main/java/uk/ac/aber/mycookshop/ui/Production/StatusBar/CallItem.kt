package uk.ac.aber.mycookshop.ui.Production.StatusBar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import uk.ac.aber.mycookshop.model.Call
import uk.ac.aber.mycookshop.ui.Production.StatusBar.status.getStatusColors
import uk.ac.aber.mycookshop.ui.Production.secondsToTimeCall

@Composable
fun CallItem(call: Call) {
    val (borderColor, backgroundColor, textColor, statusText) = getStatusColors(call.status)

    var currentTime by remember {
        mutableStateOf(call.product.cookingTimeInSeconds)
    }

//    var isProcessDone by remember {
//        mutableStateOf(false)
//    }

    LaunchedEffect(key1 = currentTime) {
        if(currentTime > 0) {
            delay(1000)
            currentTime -= 1

        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text(text = call.ilosc.toString())
        Spacer(modifier = Modifier.width(5.dp))
        Box(
            modifier = androidx.compose.ui.Modifier
                .border(2.dp, borderColor, shape = RoundedCornerShape(10.dp))
                .wrapContentSize()
                .background(backgroundColor, shape = RoundedCornerShape(10.dp))
                .padding(4.dp),

            ) {
            Text(
                text = statusText,
                color = textColor,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = secondsToTimeCall(currentTime))
    }
}
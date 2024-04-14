package uk.ac.aber.mycookshop.ui.Production.StatusBar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.ac.aber.mycookshop.model.Call
import uk.ac.aber.mycookshop.model.ProductStatus
import uk.ac.aber.mycookshop.ui.Production.StatusBar.status.getStatusColors
import uk.ac.aber.mycookshop.ui.Production.secondsToTimeCall
import uk.ac.aber.mycookshop.viewModel.ProductionViewModel

@Composable
fun CallItem(call: Call, productionViewModel: ProductionViewModel) {


    val callTime = productionViewModel.callTimeMap[call]?.collectAsState()

    val (borderColor, backgroundColor, textColor, statusText) = getStatusColors(call.status)
//    val isWastableFlow = productionViewModel.isWastable[call]



        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = call.ilosc.toString())
            Spacer(modifier = Modifier.width(5.dp))
            Box(
                modifier = Modifier
                    .border(2.dp, borderColor, shape = RoundedCornerShape(10.dp))
                    .wrapContentSize()
                    .background(backgroundColor, shape = RoundedCornerShape(10.dp))
                    .padding(4.dp)
                    .clickable {
                        if(call.status == ProductStatus.WASTABLE){
                            productionViewModel.removeCallByCall(call)
                        }
                    }
            ) {
                Text(
                    text = statusText,
                    color = textColor,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = secondsToTimeCall(callTime?.value))
        }
}


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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import uk.ac.aber.mycookshop.ui.Production.model.Call
import uk.ac.aber.mycookshop.ui.Production.model.ProductStatus
import uk.ac.aber.mycookshop.ui.Production.StatusBar.status.getStatusColors
import uk.ac.aber.mycookshop.ui.Production.secondsToTimer
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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = call.ilosc.toString(),
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .border(2.dp, borderColor, shape = RoundedCornerShape(10.dp))
                    .background(backgroundColor, shape = RoundedCornerShape(10.dp))
                    .padding(4.dp)
                    .clickable {
                        if (call.status == ProductStatus.WASTABLE) {
                            productionViewModel.removeCallByCall(call, call.status, ProductStatus.WASTE)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = statusText,
                    color = textColor,
                    textAlign = TextAlign.Center
                )
            }

            Text(
                text = secondsToTimer(callTime?.value),
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.End)

            )
            Spacer(modifier = Modifier.width(4.dp))
        }
}


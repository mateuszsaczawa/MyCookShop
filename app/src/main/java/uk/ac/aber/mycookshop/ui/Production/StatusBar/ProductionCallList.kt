package uk.ac.aber.mycookshop.ui.Production


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import uk.ac.aber.mycookshop.viewModel.ProductionViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.ac.aber.mycookshop.hardcodedData.ProductModel
import uk.ac.aber.mycookshop.model.CallQueue
import uk.ac.aber.mycookshop.ui.Production.StatusBar.CallItem
import uk.ac.aber.mycookshop.ui.Production.StatusBar.status.getStatusColors


@Composable
fun CallQueueList(productionViewModel: ProductionViewModel, product: ProductModel) {

    val allCalls = CallQueue.getCallsByProductType(product.type)


    Column {
        if (allCalls.isNotEmpty()) {
            allCalls.forEach { call ->

                CallItem(call)

            }
        }
    }
}
@Composable
fun secondsToTimeCall(seconds: Long): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val secondsAmount = seconds % 60

    val formattedTime = if (hours == 0L) {
        String.format("%02d:%02d", minutes, secondsAmount)
    } else {
        String.format("%02d:%02d:%02d", hours, minutes, secondsAmount)
    }

    return formattedTime
}
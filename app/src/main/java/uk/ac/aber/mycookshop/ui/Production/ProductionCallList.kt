package uk.ac.aber.mycookshop.ui.Production


import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import uk.ac.aber.mycookshop.viewModel.ProductionViewModel
import uk.ac.aber.mycookshop.model.ProductModel
import uk.ac.aber.mycookshop.model.CallQueue
import uk.ac.aber.mycookshop.ui.Production.StatusBar.CallItem


@Composable
fun CallQueueList(productionViewModel: ProductionViewModel, product: ProductModel) {

    val allCalls = CallQueue.getCallsByProductType(product.type)


    Column {
        if (allCalls.isNotEmpty()) {
            allCalls.forEach { call ->

                CallItem(call, productionViewModel)

            }
        }
    }
}
@Composable
fun secondsToTimeCall(seconds: Long?): String {

    seconds ?: return "" // Jeśli seconds jest null, zwróć pusty ciąg znaków

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
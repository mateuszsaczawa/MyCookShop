package uk.ac.aber.mycookshop.ui.elements.Production


import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import uk.ac.aber.mycookshop.viewModel.ProductionViewModel
import uk.ac.aber.mycookshop.ui.elements.Production.model.ProductModel
import uk.ac.aber.mycookshop.ui.elements.Production.model.CallQueue
import uk.ac.aber.mycookshop.ui.elements.Production.StatusBar.CallItem


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

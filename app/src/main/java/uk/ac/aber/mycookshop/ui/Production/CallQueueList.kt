package uk.ac.aber.mycookshop.ui.Production


import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import uk.ac.aber.mycookshop.viewModel.ProductionViewModel
import uk.ac.aber.mycookshop.ui.Production.model.ProductModel
import uk.ac.aber.mycookshop.ui.Production.model.CallQueue
import uk.ac.aber.mycookshop.ui.Production.StatusBar.CallItem


@Composable
fun CallQueueList(productionViewModel: ProductionViewModel, product: ProductModel) {

    val allCalls = CallQueue.getCallsByProductType(product.type)

//    val queueProduct = productionViewModel.queueProductMap[product.type]?.collectAsState()

    Column {
        if (allCalls.isNotEmpty()) {
            allCalls.forEach { call ->

                CallItem(call, productionViewModel)

            }
        }
    }
}

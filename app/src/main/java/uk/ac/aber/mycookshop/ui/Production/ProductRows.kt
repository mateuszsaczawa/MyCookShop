package uk.ac.aber.mycookshop.ui.Production

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.ac.aber.mycookshop.model.ProductModel
import uk.ac.aber.mycookshop.viewModel.ProductionViewModel


@Composable
fun ProductRows(
    productionViewModel: ProductionViewModel,
    items: List<ProductModel>
)
{
    LazyColumn(
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp, top = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        items.forEach { product ->
            item {
                ProductRow(productionViewModel, product)
            }
        }

    }
}
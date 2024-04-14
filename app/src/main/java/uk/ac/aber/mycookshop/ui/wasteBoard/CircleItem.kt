package uk.ac.aber.mycookshop.ui.Production

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.aber.mycookshop.model.ProductModel
import uk.ac.aber.mycookshop.model.ProductStatus
import uk.ac.aber.mycookshop.viewModel.ProductionViewModel

@Composable
fun CircleItem(
    productionViewModel: ProductionViewModel,
    product : ProductModel
) {

    val readyAmount = productionViewModel.totalAmountList.value[Pair(product.type, ProductStatus.READY)]

    Column(modifier = Modifier
        .width(60.dp))
    {
        Box(
            modifier = Modifier
                .size(50.dp)
                .padding(1.dp)
                .fillMaxSize()
                .background(Color.Transparent, CircleShape)
                .clickable {
//                    removeByProductType(product.type)
                    productionViewModel.removeCallByProductType(product.type)
                }
        ) {
            Image(
                painter = painterResource(product.image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.7F), CircleShape),
                contentScale = ContentScale.Crop
            )
            Text(
                text = readyAmount.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }
        Text(
            text = product.name,
            fontSize = 10.sp,
            color = Color.Black,
            modifier = Modifier


        )
    }
}
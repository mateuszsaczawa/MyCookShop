package uk.ac.aber.mycookshop.ui.Production.StatusBar.status

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import uk.ac.aber.mycookshop.model.ProductStatus
import uk.ac.aber.mycookshop.model.Quadruple

@Composable
fun getStatusColors(status: ProductStatus): Quadruple<Color, Color, Color, String> {
    val statusColor: Color
    val backgroundColor: Color
    val statusText: String

    when (status) {
        ProductStatus.WASTE -> {
            statusColor = Color(0xFF7E0707) // Czerwony
            backgroundColor = statusColor.copy(alpha = 0.3f) // Tło z 20% przezroczystości
            statusText = "Waste"
        }
        ProductStatus.READY -> {
            statusColor = Color(0xFFDDFF0F) // Zielony
            backgroundColor = statusColor.copy(alpha = 0.3f) // Tło z 20% przezroczystości
            statusText = "Ready"
        }
        ProductStatus.NEARLY_EXPIRY -> {
            statusColor = Color(0xFF089D05) // Niebieski
            backgroundColor = statusColor.copy(alpha = 0.3f) // Tło z 20% przezroczystośc
            statusText = "Nearly expiry"
        }
        ProductStatus.PREPARATION -> {
            statusColor = Color(0xFF110368) // Fioletowy
            backgroundColor = statusColor.copy(alpha = 0.3f) // Tło z 20% przezroczystości
            statusText = "Preparing"
        }
    }

    return Quadruple(statusColor, backgroundColor, statusColor, statusText)
}
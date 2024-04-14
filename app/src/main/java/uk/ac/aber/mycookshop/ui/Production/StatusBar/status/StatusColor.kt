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
        ProductStatus.PREPARATION -> {
            statusColor = Color(0xFF110368) // Fioletowy
            backgroundColor = statusColor.copy(alpha = 0.3f) // Tło z 20% przezroczystości
            statusText = "Preparing"
        }
        ProductStatus.READY -> {
            statusColor = Color(0xFF089D05) // Zielony
            backgroundColor = statusColor.copy(alpha = 0.3f) // Tło z 20% przezroczystości
            statusText = "Ready"
        }
        ProductStatus.WASTE -> {
            statusColor = Color(0xFF7E0707) // Czerwony
            backgroundColor = statusColor.copy(alpha = 0.3f) // Tło z 20% przezroczystości
            statusText = "Waste"
        }

        ProductStatus.WASTABLE -> {
            statusColor = Color(0xFF7E0707) // Yellow
            backgroundColor = statusColor.copy(alpha = 0.3f) // Tło z 20% przezroczystośc
            statusText = "Waste"
        }

        ProductStatus.SOLD -> {
            statusColor = Color(0xFF110368) // Fioletowy
            backgroundColor = statusColor.copy(alpha = 0.3f) // Tło z 20% przezroczystości
            statusText = "Preparing"
        }
        ProductStatus.TOTAL -> {
            statusColor = Color(0xFF110368) // Fioletowy
            backgroundColor = statusColor.copy(alpha = 0.3f) // Tło z 20% przezroczystości
            statusText = "Preparing"
        }
        ProductStatus.ERROR -> {
            statusColor = Color(0xFF110368) // Fioletowy
            backgroundColor = statusColor.copy(alpha = 0.3f) // Tło z 20% przezroczystości
            statusText = "Preparing"
        }
    }

    return Quadruple(statusColor, backgroundColor, statusColor, statusText)
}
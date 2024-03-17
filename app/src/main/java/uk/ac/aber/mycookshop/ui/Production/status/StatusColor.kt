package uk.ac.aber.mycookshop.ui.Production.status

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun getStatusColors(status: String): Triple<Color, Color, Color> {
    val borderColor = when (status) {
        "status1" -> Color(0xFF7E0707) // Czerwony
        "status2" -> Color(0xFFDDFF0F) // Zielony
        "status3" -> Color(0xFF089D05) // Niebieski
        "status4" -> Color(0xFF110368) // Fioletowy
        else -> Color.Black // Domyślny kolor
    }

    val backgroundColor = borderColor.copy(alpha = 0.2f) // Tło z 20% przezroczystości
    val textColor = Color.Black // Kolor tekstu taki sam dla wszystkich statusów

    return Triple(borderColor, backgroundColor, textColor)
}
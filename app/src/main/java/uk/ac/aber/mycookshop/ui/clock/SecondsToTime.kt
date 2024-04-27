package uk.ac.aber.mycookshop.ui.Production

import androidx.compose.runtime.Composable

@Composable
fun secondsToTimer(seconds: Int?): String {

    seconds ?: return "00:00" // Jeśli seconds jest null, zwróć pusty ciąg znaków

    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val secondsAmount = seconds % 60

    val formattedTime = if (hours == 0) {
        String.format("%02d:%02d", minutes, secondsAmount)
    } else {
        String.format("%02d:%02d:%02d", hours, minutes, secondsAmount)
    }

    return formattedTime
}
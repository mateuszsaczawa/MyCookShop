package uk.ac.aber.mycookshop.ui.screens.elements.clock

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import uk.ac.aber.mycookshop.hardcodedData.GameTime


@Composable
fun GameClock() {
    var gameTime by remember { mutableStateOf(GameTime()) }


    val list = listOf("Slow", "Normal", "Fast")
    var expanded by remember { mutableStateOf(false) }
    val currentValue = remember { mutableStateOf(list[1]) }

    GameClockUpdater(gameTime)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = "Day: ${gameTime.getDay()}",
            textAlign = TextAlign.Start)
        Text(
            text = secondsToTime(gameTime.getPlayTimeSeconds()),
            textAlign = TextAlign.Center
        )
        // Dropdown menu for Multiplier
        Box(
            modifier = Modifier
        ) {
            Text(
                text = currentValue.value,
                modifier = Modifier
                    .clickable { expanded = !expanded }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                list.forEach { it ->
                    DropdownMenuItem(
                        text = { Text(
                            text = it,
                            textAlign = TextAlign.End
                        ) },
                        onClick = {
                            currentValue.value = it
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun GameClockUpdater(gameTime: GameTime) {
    LaunchedEffect(Unit) {
        while (true) {
            withFrameNanos {
                gameTime.updatePlayTime()
            }
            delay(1000)
        }
    }
}

@Composable
fun secondsToTime(seconds: Long): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val formattedTime = String.format("%02d:%02d", hours, minutes)
    return formattedTime
}

@Composable
@Preview
fun GameClockPreview() {
    GameClock()
}
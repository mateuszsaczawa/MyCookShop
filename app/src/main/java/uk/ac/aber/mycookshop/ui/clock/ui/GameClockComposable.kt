package uk.ac.aber.mycookshop.clock

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import uk.ac.aber.mycookshop.ui.clock.model.MultiplierEnum
import uk.ac.aber.mycookshop.ui.clock.model.GameTime
import uk.ac.aber.mycookshop.viewModel.ProductionViewModel


@Composable
fun GameClockComposable(productionViewModel: ProductionViewModel) {
    val gameTime by productionViewModel.gameTime.collectAsState()
    val gameTimeSeconds by productionViewModel.timer.collectAsState()

    val list = listOf(MultiplierEnum.SLOW, MultiplierEnum.NORMAL, MultiplierEnum.FAST, MultiplierEnum.STOP)
    val currentValue by productionViewModel.currentMultiplier.collectAsState()

    val enumTextMap = mapOf(
        MultiplierEnum.SLOW to "Slow",
        MultiplierEnum.NORMAL to "Normal",
        MultiplierEnum.FAST to "Fast",
        MultiplierEnum.STOP to "Stop"
    )

    var previousMultiplier by remember { mutableStateOf(currentValue) }
    var clickedOnce by remember { mutableStateOf(false) }

    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .padding(bottom = 4.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = "Day: ${gameTime.getDay()}",
            textAlign = TextAlign.Start)
        Text(
            text = secondsToTime(gameTimeSeconds),
            textAlign = TextAlign.Center,
            modifier = Modifier.clickable {
                if(!clickedOnce) {
                    previousMultiplier = currentValue
                    clickedOnce = true
                    productionViewModel.changeMultiplier(MultiplierEnum.STOP)
                } else {
                    productionViewModel.changeMultiplier(previousMultiplier)
                    clickedOnce = false
                }

            }
        )
        // Dropdown menu for Multiplier
        Box(
            modifier = Modifier
        ) {
            Text(
                text = enumTextMap[currentValue] ?: "",
                modifier = Modifier
                    .clickable { expanded = !expanded }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                list.forEach { multiplier ->
                    DropdownMenuItem(

                        text = { Text(
                            text = enumTextMap[multiplier] ?: "",
                            textAlign = TextAlign.End
                        ) },
                        onClick = {
                            productionViewModel.changeMultiplier(multiplier)
                            expanded = false
                            previousMultiplier = multiplier
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
                gameTime.updatePlayDay()
            }
            delay(1000)
        }
    }
}

@Composable
fun  secondsToTime(seconds: Long): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val secondsAmount = seconds % 60
    val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, secondsAmount)
    return formattedTime
}
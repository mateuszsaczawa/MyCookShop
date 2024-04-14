package uk.ac.aber.mycookshop.clock

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.delay
import uk.ac.aber.mycookshop.model.MultiplierEnum
import uk.ac.aber.mycookshop.ui.clock.model.GameTime
import uk.ac.aber.mycookshop.viewModel.ProductionViewModel


@Composable
fun GameClockComposable(productionViewModel: ProductionViewModel) {
    val gameTime by productionViewModel.gameTime.collectAsState()
    val gameTimeSeconds by productionViewModel.timer.collectAsState()

    val list = listOf(MultiplierEnum.SLOW, MultiplierEnum.NORMAL, MultiplierEnum.FAST, MultiplierEnum.STOP)
    val enumTextMap = mapOf(
        MultiplierEnum.SLOW to "Slow",
        MultiplierEnum.NORMAL to "Normal",
        MultiplierEnum.FAST to "Fast",
        MultiplierEnum.STOP to "Stop"
    )


    val currentValue = remember { mutableStateOf(enumTextMap[MultiplierEnum.NORMAL]) }

    var expanded by remember { mutableStateOf(false) }
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
            text = secondsToTime(gameTimeSeconds),
            textAlign = TextAlign.Center
        )
        // Dropdown menu for Multiplier
        Box(
            modifier = Modifier
        ) {
            Text(
                text = currentValue.value ?: "",
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
                            text = enumTextMap[it] ?: "",
                            textAlign = TextAlign.End
                        ) },
                        onClick = {
                            productionViewModel.changeMultiplier(it)
                            currentValue.value = enumTextMap[it]
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
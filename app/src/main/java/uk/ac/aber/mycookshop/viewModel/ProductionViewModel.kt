package uk.ac.aber.mycookshop.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.ac.aber.mycookshop.ui.clock.model.GameTime
import java.text.SimpleDateFormat
import java.util.*

class ProductionViewModel : ViewModel() {
    private val _gameTime = GameTime()
    private val _timer = MutableStateFlow(_gameTime)

    var modi = _gameTime.getMultiplier() * 1000

    val timer = _timer.asStateFlow()

    private var timerJob: Job? = null

    init {
        startTimer()
    }

    private fun startTimer() {
//        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            repeat(Int.MAX_VALUE) {
                delay(modi)
                _gameTime.updatePlayDay() // Aktualizacja dnia gry
                _timer.value = _gameTime
            }
        }
    }

}
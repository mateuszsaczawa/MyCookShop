package uk.ac.aber.mycookshop.hardcodedData

class GameTime(
    private var _day: Int = 1,
    private var _multiplier: Int = 1,
    private var _playTimeSeconds: Long = 0
) {
    // Getter dla całego obiektu GameTime
    fun getGameTime(): GameTime {
        return this
    }

    // Getter dla multiplier
    fun getMultiplier(): Int {
        return _multiplier
    }

    // Setter dla multiplier
    fun setMultiplier(newMultiplier: Int) {
        _multiplier = newMultiplier
    }

    // Metoda do zwiekszania dnia o 1
    fun increaseDay() {
        _day++
    }

    fun getDay(): Int {
        return _day
    }

    fun getPlayTimeSeconds(): Long {
        return _playTimeSeconds
    }
    // Metoda do aktualizacji czasu gry
    fun updatePlayTime() {
        _playTimeSeconds += _multiplier

        // Jeśli czas gry przekroczy 23, zresetuj go do 9 i zwiększ dni o 1
        if (_playTimeSeconds >= 23 * 3600) {
            _playTimeSeconds = 9 * 3600
            increaseDay()
        }
    }
}
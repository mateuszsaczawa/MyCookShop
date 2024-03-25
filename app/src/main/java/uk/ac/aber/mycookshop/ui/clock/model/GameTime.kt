package uk.ac.aber.mycookshop.ui.clock.model


class GameTime(
    private var _day: Int = 1,
    private var _multiplier: Float = 1.0F,
    private var _playTimeSeconds: Long = 26100
) {

    // Getter dla całego obiektu GameTime
    fun getGameTime(): GameTime {
        return this
    }

    // Getter dla multiplier
    fun getMultiplier(): Float {
        return _multiplier
    }

    // Setter dla multiplier
    fun setMultiplier(newMultiplier: Float) {
        _multiplier = newMultiplier
    }

    // Metoda do zwiekszania dnia o 1
    fun increaseDay() {
        _day++
        _playTimeSeconds = 26100
    }

    fun getDay(): Int {
        return _day
    }

    fun getPlayTimeSeconds(): Long {
        return _playTimeSeconds
    }

    // Metoda do aktualizacji czasu gry
    fun updatePlayDay() {

        // Jeśli czas gry przekroczy 23, zresetuj go do 9 i zwiększ dni o 1
        if (_playTimeSeconds >= 23 * 3600) {
            _playTimeSeconds = 9 * 3600
            increaseDay()
        }
    }
}
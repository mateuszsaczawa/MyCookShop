package uk.ac.aber.mycookshop.ui.elements.clock.model


class GameTime(
    private var _day: Int = 1,
    private var _multiplier: Float = 1.0F,
    private var _playTimeSeconds: Long = 28800
) {

    // Getter for whole object GameTime
    fun getGameTime(): GameTime {
        return this
    }

    // Getter for multiplier
    fun getMultiplier(): Float {
        return _multiplier
    }

    // Setter for multiplier
    fun setMultiplier(newMultiplier: Float) {
        _multiplier = newMultiplier
    }

    // Method to increase day by 1
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

        //if game time will be higher than 23 hours then reset to 9 and add 1 day
        if (_playTimeSeconds >= 23 * 3600) {
            _playTimeSeconds = 9 * 3600
            increaseDay()
        }
    }
}
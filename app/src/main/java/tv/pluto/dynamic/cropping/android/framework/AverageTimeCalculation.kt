package tv.pluto.dynamic.cropping.android.framework

class AverageTimeCalculation {

    private val maxSizeOfBuffer = 60
    private val buffer = mutableListOf<Long>()

    fun calculateAverageTimeBetweenFrames(): Double? {
        val newTimestamp = System.currentTimeMillis()
        if (buffer.size == maxSizeOfBuffer) {
            buffer.removeAt(0)
        }
        buffer.add(newTimestamp)

        return if (buffer.isNotEmpty()) {
            val intervals: List<Long> = buffer.zipWithNext { a, b -> b - a }
            intervals.average()
        } else {
            null
        }
    }
}
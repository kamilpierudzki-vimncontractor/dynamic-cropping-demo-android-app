package tv.pluto.dynamic.cropping.android.logic

class AverageTimeCalculation(
    private val timestampProvider: TimestampProvider,
    private val maxSizeOfBuffer: Int = 20,
) {

    private val buffer = mutableListOf<Long>()

    fun calculateAverageTimeBetweenFrames(): Double? {
        if (buffer.size == maxSizeOfBuffer) {
            buffer.removeAt(0)
        }
        val newTimestamp = timestampProvider.timestampMs
        buffer.add(newTimestamp)

        return if (buffer.size > 1) {
            val intervals: List<Long> = buffer.zipWithNext { a, b -> b - a }
            intervals.average()
        } else {
            null
        }
    }
}
package tv.pluto.dynamic.cropping.android.logic

class LastTwoFramesTimestampDiff(
    private val timestampProvider: TimestampProvider,
) {

    private var memory: Long? = null

    fun calculateDiff(): Long? {
        val lastValue = memory
        val newTimestamp = timestampProvider.timestampMs
        val diff = if (lastValue != null) {
            newTimestamp - lastValue
        } else {
            null
        }
        memory = newTimestamp
        return diff
    }
}
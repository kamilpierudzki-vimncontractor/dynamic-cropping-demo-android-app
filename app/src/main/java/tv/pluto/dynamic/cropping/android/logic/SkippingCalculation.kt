package tv.pluto.dynamic.cropping.android.logic

import kotlin.math.absoluteValue

sealed interface Decision {
    data object Accepted : Decision
    data class TooLittleDiff(val diff: Int) : Decision
    data class SameValue(val value: Int) : Decision
}

class SkippingCalculation(private val skipIfPositionDiffIsLessThan: Int = 8) {

    private var memory: Int? = null

    fun valueAccepted(absoluteXPosition: Double): Decision {
        val newValue = absoluteXPosition.toInt()
        val lastValue = memory
        val decision = if (lastValue != null) {
            if (newValue != lastValue) {
                val absoluteDiff = (lastValue - newValue).absoluteValue
                if (absoluteDiff > skipIfPositionDiffIsLessThan) {
                    Decision.Accepted
                } else {
                    Decision.TooLittleDiff(absoluteDiff)
                }
            } else {
                Decision.SameValue(lastValue)
            }
        } else {
            Decision.Accepted
        }
        memory = newValue

        return decision
    }
}
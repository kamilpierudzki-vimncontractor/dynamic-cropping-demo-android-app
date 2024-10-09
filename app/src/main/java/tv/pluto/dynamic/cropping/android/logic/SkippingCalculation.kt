package tv.pluto.dynamic.cropping.android.logic

import kotlin.math.absoluteValue

sealed interface Decision {
    data object Accepted : Decision
    data class TooLittleDiff(val diff: Int) : Decision
    data class SameValue(val value: Int) : Decision
}

private const val SKIP_IF_POSITION_DIFF_IS_LESS_THAN = 8

class SkippingCalculation {

    private var memory: Int? = null

    fun valueAccepted(absoluteXPosition: Double): Decision {
        val newValue = absoluteXPosition.toInt()
        val lastValue = memory
        val decision = if (lastValue != null) {
            if (newValue != lastValue) {
                val diff = (lastValue - newValue)
                val absoluteDiff = diff.absoluteValue
                if (absoluteDiff > SKIP_IF_POSITION_DIFF_IS_LESS_THAN) {
                    Decision.Accepted
                } else {
                    Decision.TooLittleDiff(diff)
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
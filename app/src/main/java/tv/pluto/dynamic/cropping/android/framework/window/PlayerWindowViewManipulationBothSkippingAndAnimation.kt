package tv.pluto.dynamic.cropping.android.framework.window

import tv.pluto.dynamic.cropping.android.logic.AverageTimeCalculation
import tv.pluto.dynamic.cropping.android.logic.Decision
import tv.pluto.dynamic.cropping.android.logic.PlayerWindowViewManipulation
import tv.pluto.dynamic.cropping.android.logic.SkippingCalculation

class PlayerWindowViewManipulationBothSkippingAndAnimation(
    private val skippingCalculation: SkippingCalculation,
    private val averageTimeCalculation: AverageTimeCalculation,
    private val playerWindowViewAnimation: PlayerWindowViewAnimation,
) : PlayerWindowViewManipulation {

    override fun cancelOngoingOperations() {
        playerWindowViewAnimation.cancel()
    }

    override fun updateXPosition(absoluteXPosition: Double) {
        when (val decision = skippingCalculation.valueAccepted(absoluteXPosition)) {
            Decision.Accepted -> {
                val targetValue = absoluteXPosition.toInt()
                android.util.Log.d("test123",
                    "PlayerWindowViewManipulationBothSkippingAndAnimation, translationX=$targetValue")
                val averageDurationBetweenFrames =
                    (averageTimeCalculation.calculateAverageTimeBetweenFrames() ?: 0.0).toLong()
                playerWindowViewAnimation.start(averageDurationBetweenFrames, targetValue)
            }

            is Decision.SameValue -> {
                android.util.Log.d("test123",
                    "PlayerWindowViewManipulationBothSkippingAndAnimation, SKIPPED ($decision)")
            }

            is Decision.TooLittleDiff -> {
                android.util.Log.d("test123",
                    "PlayerWindowViewManipulationBothSkippingAndAnimation, SKIPPED ($decision)")
            }
        }
    }
}
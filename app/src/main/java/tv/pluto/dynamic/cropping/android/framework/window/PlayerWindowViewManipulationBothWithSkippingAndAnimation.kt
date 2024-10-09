package tv.pluto.dynamic.cropping.android.framework.window

import tv.pluto.dynamic.cropping.android.logic.AverageTimeCalculation
import tv.pluto.dynamic.cropping.android.logic.Decision
import tv.pluto.dynamic.cropping.android.logic.PlayerWindowViewManipulation
import tv.pluto.dynamic.cropping.android.logic.SkippingCalculation

class PlayerWindowViewManipulationBothWithSkippingAndAnimation(
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
                android.util.Log.d("test123",
                    "PlayerWindowViewManipulationBothWithSkippingAndAnimation, translationX=${absoluteXPosition.toFloat()}")
                val averageDurationBetweenFrames =
                    (averageTimeCalculation.calculateAverageTimeBetweenFrames() ?: 0.0).toLong()
                playerWindowViewAnimation.start(averageDurationBetweenFrames, absoluteXPosition.toFloat())
            }

            is Decision.SameValue -> {
                android.util.Log.d("test123",
                    "PlayerWindowViewManipulationBothWithSkippingAndAnimation, SKIPPED ($decision)")
            }

            is Decision.TooLittleDiff -> {
                android.util.Log.d("test123",
                    "PlayerWindowViewManipulationBothWithSkippingAndAnimation, SKIPPED ($decision)")
            }
        }
    }
}
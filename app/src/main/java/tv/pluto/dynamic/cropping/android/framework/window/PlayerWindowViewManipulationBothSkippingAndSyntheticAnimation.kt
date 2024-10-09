package tv.pluto.dynamic.cropping.android.framework.window

import tv.pluto.dynamic.cropping.android.logic.Decision
import tv.pluto.dynamic.cropping.android.logic.LastTwoFramesTimestampDiff
import tv.pluto.dynamic.cropping.android.logic.PlayerWindowViewManipulation
import tv.pluto.dynamic.cropping.android.logic.SkippingCalculation

class PlayerWindowViewManipulationBothSkippingAndSyntheticAnimation(
    private val skippingCalculation: SkippingCalculation,
    private val playerWindowViewSyntheticAnimation: PlayerWindowViewSyntheticAnimation,
    private val lastTwoFramesTimestampDiff: LastTwoFramesTimestampDiff,
) : PlayerWindowViewManipulation {

    override fun updateXPosition(absoluteXPosition: Double) {
        when (val decision = skippingCalculation.valueAccepted(absoluteXPosition)) {
            Decision.Accepted -> {
                val durationBetweenLastTwoFrames = lastTwoFramesTimestampDiff.calculateDiff() ?: 0L
                android.util.Log.d("test123",
                    "PlayerWindowViewManipulationBothSkippingAndSyntheticAnimation, translationX=$absoluteXPosition, durationBetweenLastTwoFrames=$durationBetweenLastTwoFrames"
                )
                playerWindowViewSyntheticAnimation.start(durationBetweenLastTwoFrames, absoluteXPosition)
            }

            is Decision.SameValue -> {
                android.util.Log.d("test123",
                    "PlayerWindowViewManipulationBothSkippingAndSyntheticAnimation, SKIPPED ($decision)")
            }

            is Decision.TooLittleDiff -> {
                android.util.Log.d("test123",
                    "PlayerWindowViewManipulationBothSkippingAndSyntheticAnimation, SKIPPED ($decision)")
            }
        }
    }

    override fun cancelOngoingOperations() {
        playerWindowViewSyntheticAnimation.cancel()
    }
}
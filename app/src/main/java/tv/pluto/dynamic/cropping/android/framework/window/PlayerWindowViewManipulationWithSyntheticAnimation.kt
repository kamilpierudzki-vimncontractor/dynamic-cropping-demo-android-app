package tv.pluto.dynamic.cropping.android.framework.window

import tv.pluto.dynamic.cropping.android.logic.LastTwoFramesTimestampDiff
import tv.pluto.dynamic.cropping.android.logic.PlayerWindowViewManipulation

class PlayerWindowViewManipulationWithSyntheticAnimation(
    private val playerWindowViewSyntheticAnimation: PlayerWindowViewSyntheticAnimation,
    private val lastTwoFramesTimestampDiff: LastTwoFramesTimestampDiff,
) : PlayerWindowViewManipulation {

    override fun updateXPosition(absoluteXPosition: Double) {
        val durationBetweenLastTwoFrames = lastTwoFramesTimestampDiff.calculateDiff() ?: 0L
        android.util.Log.d("test123",
            "PlayerWindowViewManipulationWithSyntheticAnimation, translationX=$absoluteXPosition, durationBetweenLastTwoFrames=$durationBetweenLastTwoFrames")
        playerWindowViewSyntheticAnimation.start(durationBetweenLastTwoFrames, absoluteXPosition)
    }

    override fun cancelOngoingOperations() {
        playerWindowViewSyntheticAnimation.cancel()
    }
}
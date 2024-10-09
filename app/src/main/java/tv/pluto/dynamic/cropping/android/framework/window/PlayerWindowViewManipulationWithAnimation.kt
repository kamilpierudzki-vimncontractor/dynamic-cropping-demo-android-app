package tv.pluto.dynamic.cropping.android.framework.window

import tv.pluto.dynamic.cropping.android.framework.AverageTimeCalculation
import tv.pluto.dynamic.cropping.android.logic.PlayerWindowViewManipulation

class PlayerWindowViewManipulationWithAnimation(
    private val averageTimeCalculation: AverageTimeCalculation,
    private val playerWindowViewAnimation: PlayerWindowViewAnimation,
) : PlayerWindowViewManipulation {

    override fun updateXPosition(absoluteXPosition: Double) {
        animateTranslationX(absoluteXPosition.toFloat())
    }

    override fun cancelOngoingOperations() {
        playerWindowViewAnimation.cancel()
    }

    private fun animateTranslationX(value: Float) {
        playerWindowViewAnimation.cancel()

        val averageDurationBetweenFrames = (averageTimeCalculation.calculateAverageTimeBetweenFrames() ?: 0.0).toLong()
        android.util.Log.d("test123",
            "PlayerWindowViewManipulationWithAnimation, translationX=$value, averageDurationBetweenFrames=$averageDurationBetweenFrames"
        )
        playerWindowViewAnimation.start(averageDurationBetweenFrames, value)
    }
}
package tv.pluto.dynamic.cropping.android.framework.window

import android.animation.ObjectAnimator
import android.view.View
import tv.pluto.dynamic.cropping.android.logic.PlayerWindowView

class PlayerWindowViewTranslationXWithAnimation(private val playerWindowView: View) : PlayerWindowView {

    private var animator: ObjectAnimator? = null
    private val maxSizeOfBuffer = 60
    private val buffer = mutableListOf<Long>()

    override val width: Int
        get() = playerWindowView.width
    override val height: Int
        get() = playerWindowView.height

    override fun updateXPosition(xPosition: Float) {
        animateTranslationX(xPosition)
    }

    private fun animateTranslationX(value: Float) {
        animator?.cancel()
        animator?.removeAllListeners()

        val averageDurationBetweenFrames = (calculateAverageTimeBetweenFrames() ?: 0.0).toLong()
        android.util.Log.d("test123", "PlayerWindowViewTranslationXWithAnimation, translationX=$value, averageDurationBetweenFrames=$averageDurationBetweenFrames")
        animator = ObjectAnimator.ofFloat(playerWindowView, "translationX", value).apply {
            duration = averageDurationBetweenFrames
            addUpdateListener { v ->
                val lastAnimatorValue = (v.animatedValue as Float)
                android.util.Log.d("test123", "PlayerWindowViewTranslationXWithAnimation, BETWEEN pos=$lastAnimatorValue")
            }
            start()
        }
    }

    private fun calculateAverageTimeBetweenFrames(): Double? {
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
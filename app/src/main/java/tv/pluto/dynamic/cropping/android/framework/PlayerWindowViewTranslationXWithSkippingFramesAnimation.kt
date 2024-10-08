package tv.pluto.dynamic.cropping.android.framework

import android.animation.ObjectAnimator
import android.view.View
import kotlin.math.absoluteValue
import tv.pluto.dynamic.cropping.android.logic.PlayerWindowView

class PlayerWindowViewTranslationXWithSkippingFramesAnimation(private val playerWindowView: View) : PlayerWindowView {

    private val SKIP_IF_DIFF_IS_LESS_THAN = 10
    private var memory: Int? = null

    override val width: Int
        get() = playerWindowView.width
    override val height: Int
        get() = playerWindowView.height

    override fun updateXPosition(xPosition: Float) {
        val newValue = xPosition.toInt()
        val lastValue = memory
        if (lastValue != null) {
            if (newValue != lastValue) {
                val diff = (lastValue - newValue)
                val absoluteDiff = diff.absoluteValue
                if (absoluteDiff >= SKIP_IF_DIFF_IS_LESS_THAN) {
                    animateTranslationX(newValue)
                } else {
                    android.util.Log.d("test123", "PlayerWindowViewTransitionXWithSkippingFrames, SKIPPED (diff=$diff)")
                }
            } else {
                android.util.Log.d("test123", "PlayerWindowViewTransitionXWithSkippingFrames, SKIPPED (value)")
            }
        } else {
            animateTranslationX(newValue)
        }
        memory = newValue
    }

    private fun animateTranslationX(value: Int) {
        android.util.Log.d("test123",
            "PlayerWindowViewTranslationXWithSkippingFramesAnimation, translationX=$value")
        ObjectAnimator.ofFloat(playerWindowView, "translationX", value.toFloat()).apply {
            duration = 15
            start()
        }
    }
}
package tv.pluto.dynamic.cropping.android.framework

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import kotlin.math.absoluteValue
import tv.pluto.dynamic.cropping.android.logic.PlayerWindowView

class PlayerWindowViewTranslationXWithSkippingFramesAnimation(private val playerWindowView: View) : PlayerWindowView {

    private val SKIP_IF_DIFF_IS_LESS_THAN = 0
    private var SKIP_EVERY_N_FRAMES = 99999
    private var acceptedFrameCounter = 0
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
                    if (acceptedFrameCounter <= SKIP_EVERY_N_FRAMES) {
                        acceptedFrameCounter++
                        animateTranslationX(newValue)
                    } else {
                        acceptedFrameCounter = 0
                        android.util.Log.d("test123", "PlayerWindowViewTransitionXWithSkippingFrames, SKIPPED (frame)")
                    }
                } else {
                    android.util.Log.d("test123", "PlayerWindowViewTransitionXWithSkippingFrames, SKIPPED (diff=$diff)")
                }
            } else {
                android.util.Log.d("test123", "PlayerWindowViewTransitionXWithSkippingFrames, SKIPPED (value)")
            }
        } else {
            if (acceptedFrameCounter <= SKIP_EVERY_N_FRAMES) {
                animateTranslationX(newValue)
            } else {
                acceptedFrameCounter = 0
                android.util.Log.d("test123", "PlayerWindowViewTransitionXWithSkippingFrames, SKIPPED (frame)")
            }
        }
        memory = newValue
    }

    private fun animateTranslationX(value: Int) {
        android.util.Log.d("test123",
            "PlayerWindowViewTranslationXWithSkippingFramesAnimation, translationX=$value")
        ObjectAnimator.ofFloat(playerWindowView, "translationX", value.toFloat()).apply {
            duration = 16
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }
}
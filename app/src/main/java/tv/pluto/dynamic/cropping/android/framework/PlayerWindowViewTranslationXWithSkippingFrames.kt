package tv.pluto.dynamic.cropping.android.framework

import android.view.View
import tv.pluto.dynamic.cropping.android.logic.PlayerWindowView

class PlayerWindowViewTranslationXWithSkippingFrames(private val playerWindowView: View) : PlayerWindowView {

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
                val diff = lastValue - newValue
                android.util.Log.d("test123", "PlayerWindowViewTransitionXWithSkippingFrames, lastValue: $lastValue, newValue: $newValue, diff: $diff")
                playerWindowView.translationX = newValue.toFloat()
            } else {
                android.util.Log.d("test123", "PlayerWindowViewTransitionXWithSkippingFrames, SKIPPED")
            }
        } else {
            android.util.Log.d("test123", "PlayerWindowViewTransitionXWithSkippingFrames, translationX=${newValue.toFloat()}")
            playerWindowView.translationX = newValue.toFloat()
        }
        memory = newValue
    }
}
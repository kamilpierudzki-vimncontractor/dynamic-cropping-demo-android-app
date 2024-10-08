package tv.pluto.dynamic.cropping.android.framework

import android.view.View
import tv.pluto.dynamic.cropping.android.logic.PlayerWindowView

class PlayerWindowViewTranslationX(private val playerWindowView: View) : PlayerWindowView {

    private var memory: Float? = null

    override val width: Int
        get() = playerWindowView.width
    override val height: Int
        get() = playerWindowView.height

    override fun updateXPosition(xPosition: Float) {
        android.util.Log.d("test123", "PlayerWindowViewTransitionX, translationX=$xPosition")
        playerWindowView.translationX = xPosition
        /*val lastValue = memory
        if (lastValue != null) {
            val diff = xPosition - lastValue
            android.util.Log.d("test123", "PlayerWindowViewTransitionX, translationX=$diff (lastValue: $lastValue, newValue: $xPosition, diff: $diff)")
            playerWindowView.translationX = diff
        } else {
            android.util.Log.d("test123", "PlayerWindowViewTransitionX, translationX=$xPosition")
            playerWindowView.translationX = xPosition
        }
        memory = xPosition*/
    }
}
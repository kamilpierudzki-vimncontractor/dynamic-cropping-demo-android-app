package tv.pluto.dynamic.cropping.android.framework.window

import android.view.View
import tv.pluto.dynamic.cropping.android.logic.PlayerWindowView

class PlayerWindowViewTranslationX(private val playerWindowView: View) : PlayerWindowView {

    override val width: Int
        get() = playerWindowView.width
    override val height: Int
        get() = playerWindowView.height

    override fun updateXPosition(xPosition: Float) {
        android.util.Log.d("test123", "PlayerWindowViewTransitionX, translationX=$xPosition")
        playerWindowView.translationX = xPosition
    }
}
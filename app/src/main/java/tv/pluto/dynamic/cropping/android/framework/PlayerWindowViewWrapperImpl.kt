package tv.pluto.dynamic.cropping.android.framework

import android.view.View
import tv.pluto.dynamic.cropping.android.logic.PlayerWindowViewWrapper

class PlayerWindowViewWrapperImpl(private val playerWindowView: View) : PlayerWindowViewWrapper {

    override val width: Int
        get() = playerWindowView.width
    override val height: Int
        get() = playerWindowView.height

    override fun setX(xPosition: Float) {
        playerWindowView.x = xPosition
    }
}
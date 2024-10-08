package tv.pluto.dynamic.cropping.android.framework

import android.view.View
import tv.pluto.dynamic.cropping.android.logic.PlayerWindowView

class PlayerWindowViewX(private val playerWindowView: View) : PlayerWindowView {

    private var memory: Float? = null

    override val width: Int
        get() = playerWindowView.width
    override val height: Int
        get() = playerWindowView.height

    override fun updateXPosition(xPosition: Float) {
        val lastValue = memory
        if (lastValue != null) {
            val diff = xPosition - lastValue
            android.util.Log.d("test123",
                "PlayerWindowViewX, x=$xPosition (lastValue: $lastValue, newValue: $xPosition, diff: $diff)")
        } else {
            android.util.Log.d("test123", "PlayerWindowViewX, x=$xPosition")
        }
        playerWindowView.x = xPosition
        memory = xPosition
    }
}
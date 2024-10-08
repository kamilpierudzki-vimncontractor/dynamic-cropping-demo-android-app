package tv.pluto.dynamic.cropping.android.framework

import android.view.View
import tv.pluto.dynamic.cropping.android.logic.PlayerWindowView

class PlayerWindowViewIntX(private val playerWindowView: View) : PlayerWindowView {

    private var memory: Int? = null

    override val width: Int
        get() = playerWindowView.width
    override val height: Int
        get() = playerWindowView.height

    override fun updateXPosition(xPosition: Float) {
        val newPosition = xPosition.toInt()
        val lastValue = memory
        if (lastValue != null) {
            val diff = newPosition - lastValue
            android.util.Log.d("test123",
                "PlayerWindowViewIntX, lastValue: $lastValue, newValue: $newPosition, diff: $diff")
        }
        playerWindowView.x = newPosition.toFloat()
        memory = newPosition
    }
}
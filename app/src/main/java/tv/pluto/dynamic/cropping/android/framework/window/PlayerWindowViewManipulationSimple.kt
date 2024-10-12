package tv.pluto.dynamic.cropping.android.framework.window

import android.view.View
import tv.pluto.dynamic.cropping.android.logic.PlayerWindowViewManipulation

class PlayerWindowViewManipulationSimple(private val playerWindowView: View) : PlayerWindowViewManipulation {

    override fun updateXPosition(absoluteXPosition: Double) {
        android.util.Log.d("test123", "PlayerWindowViewManipulationSimple, translationX=$absoluteXPosition")
//        playerWindowView.translationX = absoluteXPosition.toFloat()
    }

    override fun cancelOngoingOperations() {
    }
}
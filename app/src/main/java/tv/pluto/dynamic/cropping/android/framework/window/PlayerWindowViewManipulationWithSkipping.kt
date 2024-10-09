package tv.pluto.dynamic.cropping.android.framework.window

import android.view.View
import tv.pluto.dynamic.cropping.android.logic.Decision
import tv.pluto.dynamic.cropping.android.logic.PlayerWindowViewManipulation
import tv.pluto.dynamic.cropping.android.logic.SkippingCalculation

class PlayerWindowViewManipulationWithSkipping(
    private val playerWindowView: View,
    private val skippingCalculation: SkippingCalculation,
) : PlayerWindowViewManipulation {

    override fun updateXPosition(absoluteXPosition: Double) {
        when (val decision = skippingCalculation.valueAccepted(absoluteXPosition)) {
            Decision.Accepted -> {
                android.util.Log.d("test123",
                    "PlayerWindowViewManipulationWithSkipping, translationX=${absoluteXPosition.toFloat()}")
                playerWindowView.translationX = absoluteXPosition.toFloat()
            }

            is Decision.SameValue -> {
                android.util.Log.d("test123", "PlayerWindowViewManipulationWithSkipping, SKIPPED ($decision)")
            }

            is Decision.TooLittleDiff -> {
                android.util.Log.d("test123", "PlayerWindowViewManipulationWithSkipping, SKIPPED ($decision)")
            }
        }
    }

    override fun cancelOngoingOperations() {
    }
}
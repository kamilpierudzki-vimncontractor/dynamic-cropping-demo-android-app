package tv.pluto.dynamic.cropping.android.framework.window

import android.view.View
import tv.pluto.dynamic.cropping.android.framework.SystemTimestamp
import tv.pluto.dynamic.cropping.android.logic.AverageTimeCalculation
import tv.pluto.dynamic.cropping.android.logic.PlayerWindowViewManipulation
import tv.pluto.dynamic.cropping.android.logic.SkippingCalculation

sealed interface AlgorithmType {
    data object Simple : AlgorithmType
    data object OnlyWithSkipping : AlgorithmType
    data object OnlyWithAnimation : AlgorithmType
    data object BothWithSkippingAndAnimation : AlgorithmType
}

class PlayerWindowViewManipulationFactory {

    fun create(playerWindowView: View, algorithmType: AlgorithmType): PlayerWindowViewManipulation {
        val averageTimeCalculation = AverageTimeCalculation(SystemTimestamp())
        val playerWindowViewAnimation = PlayerWindowViewAnimation(playerWindowView)
        val skippingCalculation = SkippingCalculation()
        return when (algorithmType) {
            AlgorithmType.Simple -> PlayerWindowViewManipulationSimple(playerWindowView)
            AlgorithmType.OnlyWithAnimation -> PlayerWindowViewManipulationWithAnimation(
                averageTimeCalculation,
                playerWindowViewAnimation,
            )

            AlgorithmType.OnlyWithSkipping -> PlayerWindowViewManipulationWithSkipping(playerWindowView,
                skippingCalculation)

            AlgorithmType.BothWithSkippingAndAnimation -> PlayerWindowViewManipulationBothWithSkippingAndAnimation(
                skippingCalculation,
                averageTimeCalculation,
                playerWindowViewAnimation,
            )
        }
    }
}
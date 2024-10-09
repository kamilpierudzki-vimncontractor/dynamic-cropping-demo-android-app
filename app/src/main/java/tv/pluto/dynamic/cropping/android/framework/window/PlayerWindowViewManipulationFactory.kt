package tv.pluto.dynamic.cropping.android.framework.window

import android.view.View
import tv.pluto.dynamic.cropping.android.framework.SystemTimestamp
import tv.pluto.dynamic.cropping.android.logic.AverageTimeCalculation
import tv.pluto.dynamic.cropping.android.logic.LastTwoFramesTimestampDiff
import tv.pluto.dynamic.cropping.android.logic.PlayerWindowViewInfoRetriever
import tv.pluto.dynamic.cropping.android.logic.PlayerWindowViewManipulation
import tv.pluto.dynamic.cropping.android.logic.PointsBetweenGenerator
import tv.pluto.dynamic.cropping.android.logic.SkippingCalculation

sealed interface AlgorithmType {
    data object Simple : AlgorithmType
    data object OnlySkipping : AlgorithmType
    data object OnlyAnimation : AlgorithmType
    data object BothSkippingAndAnimation : AlgorithmType
    data object OnlySyntheticAnimation : AlgorithmType
    data object BothSkippingAndSyntheticAnimation : AlgorithmType
}

class PlayerWindowViewManipulationFactory {

    fun create(
        playerWindowView: View,
        algorithmType: AlgorithmType,
        playerWindowViewInfoRetriever: PlayerWindowViewInfoRetriever,
    ): PlayerWindowViewManipulation {
        val systemTimestamp = SystemTimestamp()
        val averageTimeCalculation = AverageTimeCalculation(systemTimestamp)
        val playerWindowViewAnimation = PlayerWindowViewAnimation(playerWindowView)
        val skippingCalculation = SkippingCalculation()
        val playerWindowViewSyntheticAnimation = PlayerWindowViewSyntheticAnimation(
            playerWindowViewInfoRetriever,
            PointsBetweenGenerator(),
            PlayerWindowViewViewTranslationX(playerWindowView),
        )
        val lastTwoFramesTimestampDiff = LastTwoFramesTimestampDiff(systemTimestamp)

        return when (algorithmType) {
            AlgorithmType.Simple -> PlayerWindowViewManipulationSimple(playerWindowView)

            AlgorithmType.OnlyAnimation -> PlayerWindowViewManipulationOnlyAnimation(
                averageTimeCalculation,
                playerWindowViewAnimation,
            )

            AlgorithmType.OnlySkipping -> PlayerWindowViewManipulationWithSkipping(
                playerWindowView,
                skippingCalculation,
            )

            AlgorithmType.BothSkippingAndAnimation -> PlayerWindowViewManipulationBothSkippingAndAnimation(
                skippingCalculation,
                averageTimeCalculation,
                playerWindowViewAnimation,
            )

            AlgorithmType.OnlySyntheticAnimation -> PlayerWindowViewManipulationOnlySyntheticAnimation(
                playerWindowViewSyntheticAnimation,
                lastTwoFramesTimestampDiff,
            )

            AlgorithmType.BothSkippingAndSyntheticAnimation -> PlayerWindowViewManipulationBothSkippingAndSyntheticAnimation(
                skippingCalculation,
                playerWindowViewSyntheticAnimation,
                lastTwoFramesTimestampDiff,
            )
        }
    }
}
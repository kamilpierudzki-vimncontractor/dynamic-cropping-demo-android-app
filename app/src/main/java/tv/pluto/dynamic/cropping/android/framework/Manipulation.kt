package tv.pluto.dynamic.cropping.android.framework

import com.google.android.exoplayer2.ui.StyledPlayerView
import tv.pluto.dynamic.cropping.android.framework.window.PlayerWindowViewInfoRetrieverImpl
import tv.pluto.dynamic.cropping.android.framework.window.PlayerWindowViewManipulationFactory
import tv.pluto.dynamic.cropping.android.framework.window.AlgorithmType
import tv.pluto.dynamic.cropping.android.logic.InfiniteCoordinatesProvider
import tv.pluto.dynamic.cropping.android.logic.PlayerPositionCalculation
import tv.pluto.dynamic.cropping.android.logic.PlayerWindowViewManipulation
import tv.pluto.dynamic.cropping.android.logic.Size

class Manipulation(
    playerView: StyledPlayerView,
    coordinates: DoubleArray,
    playerWindowViewManipulationFactory: PlayerWindowViewManipulationFactory,
) {

    private var playerPositionCalculation: PlayerPositionCalculation? = null
    private var playerWindowViewManipulation: PlayerWindowViewManipulation? = null

    init {
        val playerWindowViewInfoRetriever = PlayerWindowViewInfoRetrieverImpl(playerView)
        val infiniteCoordinatesProvider = InfiniteCoordinatesProvider(coordinates)
        playerPositionCalculation = PlayerPositionCalculation(
            playerWindowViewInfoRetriever,
            infiniteCoordinatesProvider,
        )
        playerWindowViewManipulation = playerWindowViewManipulationFactory.create(
            playerWindowView = playerView,
            algorithmType = AlgorithmType.OnlyWithAnimation,
        )
    }

    fun cancelOngoingOperations() {
        playerWindowViewManipulation?.cancelOngoingOperations()
    }

    fun onNewFrame() {
        playerPositionCalculation?.calculateNextAbsoluteXPosition()?.let { newAbsoluteXPosition ->
            playerWindowViewManipulation?.updateXPosition(newAbsoluteXPosition)
        }
    }

    fun onSurfaceSizeChanged(size: Size) {
        playerPositionCalculation?.videoSizeOnSurface = size
    }

    fun onVideoSizeChanged(size: Size) {
        playerPositionCalculation?.originalVideoSize = size
    }
}
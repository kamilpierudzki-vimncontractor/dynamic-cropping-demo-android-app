package tv.pluto.dynamic.cropping.android.framework

import com.google.android.exoplayer2.ui.StyledPlayerView
import tv.pluto.dynamic.cropping.android.framework.window.AlgorithmType
import tv.pluto.dynamic.cropping.android.framework.window.PlayerWindowViewInfoRetrieverImpl
import tv.pluto.dynamic.cropping.android.framework.window.PlayerWindowViewManipulationFactory
import tv.pluto.dynamic.cropping.android.logic.InfiniteCoordinatesProvider
import tv.pluto.dynamic.cropping.android.logic.PlayerPositionCalculation
import tv.pluto.dynamic.cropping.android.logic.PlayerWindowViewManipulation
import tv.pluto.dynamic.cropping.android.logic.Size

class Manipulation(
    playerView: StyledPlayerView,
    coordinates: DoubleArray,
    playerWindowViewManipulationFactory: PlayerWindowViewManipulationFactory,
) { // todo remove

    private var playerPositionCalculation: PlayerPositionCalculation? = null
    private var playerWindowViewManipulation: PlayerWindowViewManipulation? = null

    init {
        val infiniteCoordinatesProvider = InfiniteCoordinatesProvider(coordinates)
        val playerWindowViewInfoRetriever = PlayerWindowViewInfoRetrieverImpl(playerView)
        playerPositionCalculation = PlayerPositionCalculation(
            playerWindowViewInfoRetriever,
            infiniteCoordinatesProvider,
        )
        playerWindowViewManipulation = playerWindowViewManipulationFactory.create(
            playerWindowView = playerView,
            algorithmType = AlgorithmType.Simple,
            playerWindowViewInfoRetriever = playerWindowViewInfoRetriever,
        )
    }

    fun cancelOngoingOperations() {
        playerWindowViewManipulation?.cancelOngoingOperations()
    }

    fun onNewFrame(
        videoSizeOnSurface: Size,
        originalVideoSize: Size,
    ) {
        playerPositionCalculation?.calculateNextAbsoluteXPosition(
            videoSizeOnSurface,
            originalVideoSize,
        )?.let { newAbsoluteXPosition ->
            playerWindowViewManipulation?.updateXPosition(newAbsoluteXPosition)
        }
    }

    fun onNewFrameV2(videoSizeOnSurface: Size, originalVideoSize: Size): Double? {
        return playerPositionCalculation?.calculateNextAbsoluteXPosition(videoSizeOnSurface, originalVideoSize)
    }

    fun onSurfaceSizeChanged(size: Size) {
    }

    fun onVideoSizeChanged(size: Size) {
    }
}
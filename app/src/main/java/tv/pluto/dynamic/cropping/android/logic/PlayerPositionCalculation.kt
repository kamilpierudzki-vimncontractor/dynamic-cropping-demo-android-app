package tv.pluto.dynamic.cropping.android.logic

class PlayerPositionCalculation(
    private val playerWindowViewInfoRetriever: PlayerWindowViewInfoRetriever,
    private val infiniteCoordinatesProvider: InfiniteCoordinatesProvider,
) {

    /*
    * playerWindowView - It represents the window the user sees. The cropped video must fit in it.
    * surfaceView - It is owned by the player window but it has its own width and height. It represents width and height
    * of the scaled video played on it.
    * */

    fun calculateNextAbsoluteXPosition(
        videoSizeOnSurface: Size,
        originalVideoSize: Size,
    ): Double? {
        val coordinate = infiniteCoordinatesProvider.getNextCoordinate()
        /*if (videoSizeOnSurface.isEmpty || originalVideoSize.isEmpty) {
            return null
        }*/

        return movePlayerWindowView(
            coordinate,
            videoSizeOnSurface,
            originalVideoSize,
        )
    }

    private fun movePlayerWindowView(
        coordinate: Double,
        videoSizeOnSurface: Size,
        originalVideoSize: Size,
    ): Double {
        val originalVideoWidthAndSurfaceVideoWidthRatio =
            (1.0 * videoSizeOnSurface.width.value) / originalVideoSize.width.value
        val coordinateAdjustedToSurfaceVideoWidth = coordinate * originalVideoWidthAndSurfaceVideoWidthRatio
        val centerXPositionOfVideoOnSurface = videoSizeOnSurface.width.value / 2.0
        val halfOfWidthOfPlayerWindow = playerWindowViewInfoRetriever.width / 2.0

        val pixelsToAddToCoordinate = if (willSurfaceVideoMoveTooMuchLeftOnPlayerWindow(coordinateAdjustedToSurfaceVideoWidth, halfOfWidthOfPlayerWindow, videoSizeOnSurface, )) {
            val pixelsOfPlayerWindowOutsideOfVideoSizeOnSurface = coordinateAdjustedToSurfaceVideoWidth + halfOfWidthOfPlayerWindow - videoSizeOnSurface.width.value
            -1.0 * pixelsOfPlayerWindowOutsideOfVideoSizeOnSurface
        } else if (willSurfaceVideoMoveTooMuchRightOnPlayerWindow(coordinateAdjustedToSurfaceVideoWidth, halfOfWidthOfPlayerWindow)) {
            val pixelsOfPlayerWindowOutsideOfVideoSizeOnSurface = halfOfWidthOfPlayerWindow - coordinateAdjustedToSurfaceVideoWidth
            pixelsOfPlayerWindowOutsideOfVideoSizeOnSurface
        } else {
            0.0
        }

        val coordinateAdjustedToEdgeOfVideoSizeOnSurfaceAndPlayerWindowWidth = coordinateAdjustedToSurfaceVideoWidth + pixelsToAddToCoordinate
        val absoluteXPositionOfSurfaceVideo = centerXPositionOfVideoOnSurface - coordinateAdjustedToEdgeOfVideoSizeOnSurfaceAndPlayerWindowWidth
        return absoluteXPositionOfSurfaceVideo
    }

    private fun willSurfaceVideoMoveTooMuchLeftOnPlayerWindow(
        coordinateAdjustedToSurfaceVideoWidth: Double,
        halfOfWidthOfPlayerWindow: Double,
        videoSizeOnSurface: Size,
    ): Boolean = coordinateAdjustedToSurfaceVideoWidth + halfOfWidthOfPlayerWindow > videoSizeOnSurface.width.value

    private fun willSurfaceVideoMoveTooMuchRightOnPlayerWindow(
        coordinateAdjustedToSurfaceVideoWidth: Double,
        halfOfWidthOfPlayerWindow: Double,
    ): Boolean = coordinateAdjustedToSurfaceVideoWidth - halfOfWidthOfPlayerWindow < 0
}

private val Size.isEmpty: Boolean get() = width.value == 0 || height.value == 0
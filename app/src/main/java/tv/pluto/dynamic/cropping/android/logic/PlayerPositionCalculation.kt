package tv.pluto.dynamic.cropping.android.logic

class PlayerPositionCalculation(
    private val playerWindowView: PlayerWindowView,
    private val infiniteCoordinatesProvider: InfiniteCoordinatesProvider,
) {

    /*
    * playerWindowView - It represents the window the user sees. The cropped video must fit in it.
    * surfaceView - It is owned by the player window but it has its own width and height. It represents width and height
    * of the scaled video played on it.
    * */

    var videoSizeOnSurface = Size(Width(0), Height(0))
    var originalVideoSize = Size(Width(0), Height(0))

    fun onNewFrame() {
        val coordinate = infiniteCoordinatesProvider.getNextCoordinate()
        if (videoSizeOnSurface.isEmpty || originalVideoSize.isEmpty) {
            return
        }

        movePlayerWindowView(coordinate)
    }

    private fun movePlayerWindowView(coordinate: Double) {
        val originalVideoWidthAndSurfaceVideoWidthRatio =
            (1.0 * videoSizeOnSurface.width.value) / originalVideoSize.width.value
        val coordinateAdjustedToSurfaceVideoWidth = coordinate * originalVideoWidthAndSurfaceVideoWidthRatio
        val centerXPositionOfVideoOnSurface = videoSizeOnSurface.width.value / 2.0
        val halfOfWidthOfPlayerWindow = playerWindowView.width / 2.0

        val pixelsToAddToCoordinate = if (willSurfaceVideoMoveTooMuchLeftOnPlayerWindow(
                coordinateAdjustedToSurfaceVideoWidth,
                halfOfWidthOfPlayerWindow)) {
            val pixelsOfPlayerWindowOutsideOfVideoSizeOnSurface =
                coordinateAdjustedToSurfaceVideoWidth + halfOfWidthOfPlayerWindow - videoSizeOnSurface.width.value
            -1.0 * pixelsOfPlayerWindowOutsideOfVideoSizeOnSurface
        } else if (willSurfaceVideoMoveTooMuchRightOnPlayerWindow(coordinateAdjustedToSurfaceVideoWidth,
                halfOfWidthOfPlayerWindow)) {
            val pixelsOfPlayerWindowOutsideOfVideoSizeOnSurface =
                halfOfWidthOfPlayerWindow - coordinateAdjustedToSurfaceVideoWidth
            pixelsOfPlayerWindowOutsideOfVideoSizeOnSurface
        } else {
            0.0
        }

        val coordinateAdjustedToEdgeOfVideoSizeOnSurfaceAndPlayerWindowWidth = coordinateAdjustedToSurfaceVideoWidth + pixelsToAddToCoordinate
        val absoluteXPositionOfSurfaceVideo = centerXPositionOfVideoOnSurface - coordinateAdjustedToEdgeOfVideoSizeOnSurfaceAndPlayerWindowWidth
        playerWindowView.updateXPosition(absoluteXPositionOfSurfaceVideo.toFloat())
    }

    // wykrycie przesunięcia wideo wyświetlanego na ekranie w lewo poza okno widoku playera
    private fun willSurfaceVideoMoveTooMuchLeftOnPlayerWindow(
        coordinateAdjustedToSurfaceVideoWidth: Double,
        halfOfWidthOfPlayerWindow: Double,
    ): Boolean = coordinateAdjustedToSurfaceVideoWidth + halfOfWidthOfPlayerWindow > videoSizeOnSurface.width.value

    // wykrycie przesunięcia wideo wyświetlanego na ekranie w prawo poza okno widoku playera
    private fun willSurfaceVideoMoveTooMuchRightOnPlayerWindow(
        coordinateAdjustedToSurfaceVideoWidth: Double,
        halfOfWidthOfPlayerWindow: Double,
    ): Boolean = coordinateAdjustedToSurfaceVideoWidth - halfOfWidthOfPlayerWindow < 0
}

private val Size.isEmpty: Boolean get() = width.value == 0 || height.value == 0
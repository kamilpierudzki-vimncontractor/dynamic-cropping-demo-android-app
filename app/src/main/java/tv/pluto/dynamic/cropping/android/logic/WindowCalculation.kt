package tv.pluto.dynamic.cropping.android.logic

class WindowCalculation(
    private val scaleCoordinate: ScaleCoordinate,
    private val calculateCoordinateOffset: CalculateCoordinateOffset,
) {

    fun calculateAbsoluteTranslationX(
        coordinate: Double,
        videoResolution: VideoResolution,
        textureViewWidth: Int,
        textureSize: TextureSize,
    ): Double {
        val scaledCoordinate = scaleCoordinate.scaled(videoResolution, textureSize, coordinate)
        val offset = calculateCoordinateOffset.calculate(scaledCoordinate, textureViewWidth, textureSize)
        val coordinateWithOffset = scaledCoordinate + offset
        val absoluteXPositionOfLeftEdgeOfTexture = (textureViewWidth / 2.0) - coordinateWithOffset
        return absoluteXPositionOfLeftEdgeOfTexture
    }
}
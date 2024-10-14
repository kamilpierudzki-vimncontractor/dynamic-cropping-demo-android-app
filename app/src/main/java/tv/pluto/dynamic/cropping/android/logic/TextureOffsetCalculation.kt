package tv.pluto.dynamic.cropping.android.logic

class TextureOffsetCalculation(
    private val scaleCoordinate: ScaleCoordinate,
    private val offScreenOffsetCalculation: OffScreenOffsetCalculation,
) {

    fun calculateXAxisAbsoluteOffset(
        coordinate: Double,
        videoResolution: VideoResolution,
        textureViewWidth: Int,
        textureSize: TextureSize,
    ): Double {
        val scaledCoordinate = scaleCoordinate.scaled(videoResolution, textureSize, coordinate)
        val offScreenOffset = offScreenOffsetCalculation.calculate(scaledCoordinate, textureViewWidth, textureSize)
        val coordinateWithOffset = scaledCoordinate + offScreenOffset
        val xAxisAbsoluteOffset = (textureViewWidth / 2.0) - coordinateWithOffset
        return xAxisAbsoluteOffset
    }
}
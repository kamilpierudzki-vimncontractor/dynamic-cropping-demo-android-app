package tv.pluto.dynamic.cropping.android.logic

class CalculateTextureXAxisAbsoluteOffset(
    private val scaleCoordinate: ScaleCoordinate,
    private val calculateOffScreenOffset: CalculateOffScreenOffset,
) {

    fun calculated(
        coordinate: Double,
        videoResolution: VideoResolution,
        textureViewWidth: Int,
        textureSize: TextureSize,
    ): Double {
        val scaledCoordinate = scaleCoordinate.scaled(videoResolution, textureSize, coordinate)
        val offScreenOffset = calculateOffScreenOffset.calculated(scaledCoordinate, textureViewWidth, textureSize)
        val coordinateWithOffset = scaledCoordinate + offScreenOffset
        val xAxisAbsoluteOffset = (textureViewWidth / 2.0) - coordinateWithOffset
        return xAxisAbsoluteOffset
    }
}
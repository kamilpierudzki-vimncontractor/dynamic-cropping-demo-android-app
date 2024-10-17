package tv.pluto.dynamic.cropping.android.logic

class CalculateOffScreenOffset {

    fun calculated(textureCoordinate: Double, textureViewWidth: Int, textureSize: TextureSize): Double =
        if (textureGoTooFarRight(textureCoordinate, textureViewWidth, textureSize)) {
            val diff = (textureViewWidth / 2.0) - textureCoordinate
            diff
        } else if (textureGoTooFarLeft(textureCoordinate, textureSize, textureViewWidth)) {
            val pixelsToRightOfCoordinateOnTexture = textureSize.width.value - textureCoordinate
            val diff = (textureViewWidth / 2.0) - pixelsToRightOfCoordinateOnTexture
            -1.0 * diff
        } else {
            0.0
        }

    private fun textureGoTooFarRight(
        textureCoordinate: Double,
        textureViewWidth: Int,
        textureSize: TextureSize,
    ): Boolean {
        val pixelsToRightOfCoordOnTexture = textureSize.width.value - textureCoordinate
        return textureCoordinate < (textureViewWidth / 2.0) && pixelsToRightOfCoordOnTexture > (textureViewWidth / 2.0)
    }

    private fun textureGoTooFarLeft(
        textureCoordinate: Double,
        textureSize: TextureSize,
        textureViewWidth: Int,
    ): Boolean {
        val pixelsToRightOfCoordOnTexture = textureSize.width.value - textureCoordinate
        return textureCoordinate > (textureViewWidth / 2.0) && pixelsToRightOfCoordOnTexture < (textureViewWidth / 2.0)
    }
}
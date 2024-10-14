package tv.pluto.dynamic.cropping.android.logic

class OffScreenOffsetCalculation {

    fun calculate(textureCoordinate: Double, textureViewWidth: Int, textureSize: TextureSize): Double =
        if (textureGoTooFarRight(textureCoordinate, textureViewWidth, textureSize)) {
            val diff = (textureViewWidth / 2.0) - textureCoordinate
            diff
        } else if (textureGoTooFarLeft(textureCoordinate, textureSize, textureViewWidth)) {
            val pixelsToRightOfCoordOnTexture = textureSize.value.width.value - textureCoordinate
            val diff = (textureViewWidth / 2.0) - pixelsToRightOfCoordOnTexture
            -1.0 * diff
        } else {
            0.0
        }

    private fun textureGoTooFarRight(
        textureCoordinate: Double,
        textureViewWidth: Int,
        textureSize: TextureSize,
    ): Boolean {
        val pixelsToRightOfCoordOnTexture = textureSize.value.width.value - textureCoordinate
        return textureCoordinate < (textureViewWidth / 2.0) && pixelsToRightOfCoordOnTexture > (textureViewWidth / 2.0)
    }

    private fun textureGoTooFarLeft(
        textureCoordinate: Double,
        textureSize: TextureSize,
        textureViewWidth: Int,
    ): Boolean {
        val pixelsToRightOfCoordOnTexture = textureSize.value.width.value - textureCoordinate
        return textureCoordinate > (textureViewWidth / 2.0) && pixelsToRightOfCoordOnTexture < (textureViewWidth / 2.0)
    }
}
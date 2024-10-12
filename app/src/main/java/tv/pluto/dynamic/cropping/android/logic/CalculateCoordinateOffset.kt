package tv.pluto.dynamic.cropping.android.logic

class CalculateCoordinateOffset {

    fun calculate(textureCoordinate: Double, textureViewWidth: Int, textureSize: TextureSize): Double =
        if (textureGoTooFarRight(textureCoordinate, textureViewWidth, textureSize)) { // todo
            val diff = (textureViewWidth / 2.0) - textureCoordinate
            diff
        } else if (textureGoTooFarLeft(textureCoordinate, textureSize, textureViewWidth)) { // ta ścieżka gotowa
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

    private fun calculateIntersect(first: Pair<Int, Int>, second: Pair<Int, Int>): Pair<Int, Int>? {
        val commonStart = maxOf(first.first, second.first)
        val commonEnd = minOf(first.second, second.second)
        return if (commonStart <= commonEnd) {
            Pair(commonStart, commonEnd)
        } else {
            null
        }
    }
}
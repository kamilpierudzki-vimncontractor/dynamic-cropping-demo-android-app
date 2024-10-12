package tv.pluto.dynamic.cropping.android.logic

class ScaleCoordinate {

    fun scaled(videoResolution: VideoResolution, textureSize: TextureSize, coordinate: Double): Double{
        val widthRatio = (1.0 * textureSize.value.width.value) / videoResolution.value.width.value
        return coordinate * widthRatio
    }
}
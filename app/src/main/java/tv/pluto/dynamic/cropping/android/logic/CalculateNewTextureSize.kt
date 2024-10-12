package tv.pluto.dynamic.cropping.android.logic

import kotlin.math.roundToInt

class CalculateNewTextureSize {

    fun calculate(videoResolution: VideoResolution, textureViewSize: Size): TextureSize {
        val aspectRatio = (1.0 * videoResolution.value.width.value) / videoResolution.value.height.value
        return TextureSize(
            Size(
                Width((1.0 * textureViewSize.height.value * aspectRatio).roundToInt()),
                Height((1.0 * textureViewSize.width.value * aspectRatio).roundToInt()),
            )
        )
    }
}
package tv.pluto.dynamic.cropping.android.logic

import kotlin.math.roundToInt

class CalculateNewTextureSize {

    fun calculate(videoResolution: VideoResolution, textureViewSize: Size): TextureSize {
        if (videoResolution.value.height.value == 0) {
            return TextureSize(Size(Width(1), Height(1)))
        }
        val scale = textureViewSize.height.value.toFloat() / videoResolution.value.height.value.toFloat()
        val newWidth = (videoResolution.value.width.value * scale).roundToInt()
        return TextureSize(Size(Width(newWidth), Height(textureViewSize.height.value)))
    }
}
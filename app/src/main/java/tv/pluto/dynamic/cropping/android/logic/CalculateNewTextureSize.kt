package tv.pluto.dynamic.cropping.android.logic

import kotlin.math.roundToInt

class CalculateNewTextureSize {

    fun calculate(videoResolution: VideoResolution, textureViewSize: TextureViewSize): TextureSize {
        if (videoResolution.height.value == 0) {
            return TextureSize(Width(1), Height(1))
        }
        val scale = textureViewSize.height.value.toFloat() / videoResolution.height.value.toFloat()
        val newWidth = (videoResolution.width.value * scale).roundToInt()
        return TextureSize(Width(newWidth), Height(textureViewSize.height.value))
    }
}
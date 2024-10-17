package tv.pluto.dynamic.cropping.android.logic

import org.junit.Assert.assertEquals
import org.junit.Test

class CalculateNewTextureSizeTest {

    @Test
    fun test_1() {
        // given
        val videoResolution = VideoResolution(Width(1920), Height(1080))
        val textureViewSize = TextureViewSize(Width(1280), Height(720))

        // when
        val result = CalculateNewTextureSize().calculate(videoResolution, textureViewSize)

        // then
        assertEquals(1280, result.width.value)
        assertEquals(720, result.height.value)
    }

    @Test
    fun test_2() {
        // given
        val videoResolution = VideoResolution(Width(1024), Height(768))
        val textureViewSize = TextureViewSize(Width(1280), Height(720))

        // when
        val result = CalculateNewTextureSize().calculate(videoResolution, textureViewSize)

        // then
        assertEquals(960, result.width.value)
        assertEquals(720, result.height.value)
    }

    @Test
    fun test_3() {
        // given
        val videoResolution = VideoResolution(Width(1920), Height(0))
        val textureViewSize = TextureViewSize(Width(1280), Height(720))

        // when
        val result = CalculateNewTextureSize().calculate(videoResolution, textureViewSize)

        // then
        assertEquals(1, result.width.value)
        assertEquals(1, result.height.value)
    }

    @Test
    fun test_4() {
        // given
        val videoResolution = VideoResolution(Width(640), Height(480))
        val textureViewSize = TextureViewSize(Width(1920), Height(1080))

        // when
        val result = CalculateNewTextureSize().calculate(videoResolution, textureViewSize)

        // then
        assertEquals(1440, result.width.value)
        assertEquals(1080, result.height.value)
    }
}
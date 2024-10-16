package tv.pluto.dynamic.cropping.android.logic

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class CalculateNewTextureSizeTest {

    @Test
    fun test_1() {
        // given
        val videoResolution = VideoResolution(Size(Width(1920), Height(1080))) // 16:9
        val textureViewSize = Size(Width(1280), Height(720))  // 16:9

        // when
        val result = CalculateNewTextureSize().calculate(videoResolution, textureViewSize)

        // then
        assertEquals(1280, result.value.width.value)
        assertEquals(720, result.value.height.value)
    }

    @Test
    fun test_2() {
        // given
        val videoResolution = VideoResolution(Size(Width(1024), Height(768)))  // 4:3
        val textureViewSize = Size(Width(1280), Height(720))  // 16:9

        // when
        val result = CalculateNewTextureSize().calculate(videoResolution, textureViewSize)

        // then
        assertEquals(960, result.value.width.value)  // 720 * (1024 / 768) = 960
        assertEquals(720, result.value.height.value)
    }

    @Test
    fun test_3() {
        // given
        val videoResolution = VideoResolution(Size(Width(1920), Height(0)))
        val textureViewSize = Size(Width(1280), Height(720))

        // when
        val result = CalculateNewTextureSize().calculate(videoResolution, textureViewSize)

        // then
        assertEquals(1, result.value.width.value)  // 720 * (1024 / 768) = 960
        assertEquals(1, result.value.height.value)
    }

    @Test
    fun test_4() {
        // given
        val videoResolution = VideoResolution(Size(Width(640), Height(480)))
        val textureViewSize = Size(Width(1920), Height(1080))

        // when
        val result = CalculateNewTextureSize().calculate(videoResolution, textureViewSize)

        // then
        assertEquals(1440, result.value.width.value)  // 1080 * (640 / 480) = 1440
        assertEquals(1080, result.value.height.value)
    }
}
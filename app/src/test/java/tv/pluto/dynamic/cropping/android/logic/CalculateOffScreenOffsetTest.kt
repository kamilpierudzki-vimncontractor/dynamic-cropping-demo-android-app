package tv.pluto.dynamic.cropping.android.logic

import org.junit.Assert.assertEquals
import org.junit.Test

class CalculateOffScreenOffsetTest {

    private val calculateOffScreenOffset = CalculateOffScreenOffset()

    @Test
    fun `should return 0 when texture is centered`() {
        val textureCoordinate = 100.0
        val textureViewWidth = 200
        val textureSize = TextureSize(Width(200), Height(100))

        val result = calculateOffScreenOffset.calculated(textureCoordinate, textureViewWidth, textureSize)

        assertEquals(0.0, result, 0.0)
    }

    @Test
    fun `should return positive offset when texture goes too far right`() {
        val textureCoordinate = 50.0
        val textureViewWidth = 200
        val textureSize = TextureSize(Width(300), Height(100))

        val result = calculateOffScreenOffset.calculated(textureCoordinate, textureViewWidth, textureSize)

        assertEquals(50.0, result, 0.0)
    }

    @Test
    fun `should return negative offset when texture goes too far left`() {
        val textureCoordinate = 150.0
        val textureViewWidth = 200
        val textureSize = TextureSize(Width(200), Height(100))

        val result = calculateOffScreenOffset.calculated(textureCoordinate, textureViewWidth, textureSize)

        assertEquals(-50.0, result, 0.0)
    }

    @Test
    fun `should return 0 when texture is not off screen on either side`() {
        val textureCoordinate = 100.0
        val textureViewWidth = 300
        val textureSize = TextureSize(Width(400), Height(200))

        val result = calculateOffScreenOffset.calculated(textureCoordinate, textureViewWidth, textureSize)

        assertEquals(50.0, result, 0.0)
    }

    @Test
    fun `should return positive offset for edge case of texture too far right`() {
        val textureCoordinate = 10.0
        val textureViewWidth = 100
        val textureSize = TextureSize(Width(150), Height(100))

        val result = calculateOffScreenOffset.calculated(textureCoordinate, textureViewWidth, textureSize)

        assertEquals(40.0, result, 0.0)
    }

    @Test
    fun `should return negative offset for edge case of texture too far left`() {
        val textureCoordinate = 90.0
        val textureViewWidth = 100
        val textureSize = TextureSize(Width(100), Height(100))

        val result = calculateOffScreenOffset.calculated(textureCoordinate, textureViewWidth, textureSize)

        assertEquals(-40.0, result, 0.0)
    }
}
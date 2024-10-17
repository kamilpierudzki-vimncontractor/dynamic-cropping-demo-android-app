package tv.pluto.dynamic.cropping.android.logic

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class CalculateTextureXAxisAbsoluteOffsetTest {

    private lateinit var calculateTextureXAxisAbsoluteOffset: CalculateTextureXAxisAbsoluteOffset
    private lateinit var scaleCoordinate: ScaleCoordinate
    private lateinit var calculateOffScreenOffset: CalculateOffScreenOffset

    @Before
    fun setUp() {
        scaleCoordinate = mock(ScaleCoordinate::class.java)
        calculateOffScreenOffset = mock(CalculateOffScreenOffset::class.java)

        calculateTextureXAxisAbsoluteOffset = CalculateTextureXAxisAbsoluteOffset(
            scaleCoordinate,
            calculateOffScreenOffset
        )
    }

    @Test
    fun `should calculate correct xAxisAbsoluteOffset when coordinate is scaled and has no offscreen offset`() {
        val coordinate = 100.0
        val videoResolution = VideoResolution(Width(1920), Height(1080))
        val textureViewWidth = 1000
        val textureSize = TextureSize(Width(2000), Height(1000))

        `when`(scaleCoordinate.scaled(videoResolution, textureSize, coordinate)).thenReturn(500.0)
        `when`(calculateOffScreenOffset.calculated(500.0, textureViewWidth, textureSize)).thenReturn(0.0)

        val result =
            calculateTextureXAxisAbsoluteOffset.calculated(coordinate, videoResolution, textureViewWidth, textureSize)

        assertEquals(0.0, result, 0.01)
    }

    @Test
    fun `should calculate correct xAxisAbsoluteOffset when there is positive offscreen offset`() {
        val coordinate = 100.0
        val videoResolution = VideoResolution(Width(1920), Height(1080))
        val textureViewWidth = 1000
        val textureSize = TextureSize(Width(2000), Height(1000))

        `when`(scaleCoordinate.scaled(videoResolution, textureSize, coordinate)).thenReturn(500.0)
        `when`(calculateOffScreenOffset.calculated(500.0, textureViewWidth, textureSize)).thenReturn(100.0)

        val result =
            calculateTextureXAxisAbsoluteOffset.calculated(coordinate, videoResolution, textureViewWidth, textureSize)

        assertEquals(-100.0, result, 0.01)
    }

    @Test
    fun `should calculate correct xAxisAbsoluteOffset when there is negative offscreen offset`() {
        val coordinate = 100.0
        val videoResolution = VideoResolution(Width(1920), Height(1080))
        val textureViewWidth = 1000
        val textureSize = TextureSize(Width(2000), Height(1000))

        `when`(scaleCoordinate.scaled(videoResolution, textureSize, coordinate)).thenReturn(500.0)
        `when`(calculateOffScreenOffset.calculated(500.0, textureViewWidth, textureSize)).thenReturn(-100.0)

        val result =
            calculateTextureXAxisAbsoluteOffset.calculated(coordinate, videoResolution, textureViewWidth, textureSize)

        assertEquals(100.0, result, 0.01)
    }

    @Test
    fun `should return center of TextureView width when scaled coordinate equals TextureView center`() {
        val coordinate = 100.0
        val videoResolution = VideoResolution(Width(1920), Height(1080))
        val textureViewWidth = 1000
        val textureSize = TextureSize(Width(2000), Height(1000))

        `when`(scaleCoordinate.scaled(videoResolution, textureSize, coordinate)).thenReturn(textureViewWidth / 2.0)
        `when`(calculateOffScreenOffset.calculated(textureViewWidth / 2.0, textureViewWidth, textureSize)).thenReturn(
            0.0)

        val result =
            calculateTextureXAxisAbsoluteOffset.calculated(coordinate, videoResolution, textureViewWidth, textureSize)

        assertEquals(0.0, result, 0.01)
    }
}

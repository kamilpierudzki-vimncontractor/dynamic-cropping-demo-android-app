package tv.pluto.dynamic.cropping.android.logic

import org.junit.Assert
import org.junit.Test

class ScaleCoordinateTest {

    private val tested = ScaleCoordinate()

    @Test
    fun test_1() {
        // given
        val videoResolution = VideoResolution(Width(1000), Height(500))
        val textureSize = TextureSize(Width(2000), Height(1000))
        val coordinate = 2.5

        // when
        val actual = tested.scaled(videoResolution, textureSize, coordinate)

        // then
        Assert.assertEquals(5.0, actual, 0.0)
    }
}
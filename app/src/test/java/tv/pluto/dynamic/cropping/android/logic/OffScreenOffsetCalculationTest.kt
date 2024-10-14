package tv.pluto.dynamic.cropping.android.logic

import org.junit.Test

class OffScreenOffsetCalculationTest {

    private val tested = OffScreenOffsetCalculation()

    @Test
    fun test_1() {
        // given
        val coordinate = 2.0
        val textureViewWidth = 6
        val textureSize = TextureSize(Size(Width(8), Height(1)))

        // when
        val actual = tested.calculate(coordinate, textureViewWidth, textureSize)

        // then
//        Assert.assertEquals()
    }
}
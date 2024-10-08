package tv.pluto.dynamic.cropping.android.framework

import org.junit.Assert
import org.junit.Test
import tv.pluto.dynamic.cropping.android.logic.InfiniteCoordinatesProvider

class InfiniteCoordinatesProviderTest {

    @Test
    fun test_1() {
        // given
        val array = doubleArrayOf(1.0, 2.0, 3.0)
        val tested = InfiniteCoordinatesProvider(array)

        // when
        val result1 = tested.getNextCoordinate()
        val result2 = tested.getNextCoordinate()
        val result3 = tested.getNextCoordinate()
        val result4 = tested.getNextCoordinate()

        // then
        Assert.assertEquals(1.0, result1, 0.0)
        Assert.assertEquals(2.0, result2, 0.0)
        Assert.assertEquals(3.0, result3, 0.0)
        Assert.assertEquals(1.0, result4, 0.0)
    }
}
package tv.pluto.dynamic.cropping.android.logic

import org.junit.Assert
import org.junit.Test

class CounterBasedCoordinatesProviderTest {

    private val coordinates = doubleArrayOf(1.0, 2.0, 3.0)

    @Test
    fun test_1() {
        // given
        val tested = CounterBasedCoordinatesProvider(coordinates)

        // when
        val (coordinate1, consumedIndex1) = tested.getNextCoordinate()
        val (coordinate2, consumedIndex2) = tested.getNextCoordinate()
        val (coordinate3, consumedIndex3) = tested.getNextCoordinate()
        val (coordinate4, consumedIndex4) = tested.getNextCoordinate()
        val (coordinate5, consumedIndex5) = tested.getNextCoordinate()

        // then
        Assert.assertEquals(1.0, coordinate1, 0.0)
        Assert.assertEquals(0, consumedIndex1)

        Assert.assertEquals(2.0, coordinate2, 0.0)
        Assert.assertEquals(1, consumedIndex2)

        Assert.assertEquals(3.0, coordinate3, 0.0)
        Assert.assertEquals(2, consumedIndex3)

        Assert.assertEquals(1.0, coordinate4, 0.0)
        Assert.assertEquals(0, consumedIndex4)

        Assert.assertEquals(2.0, coordinate5, 0.0)
        Assert.assertEquals(1, consumedIndex5)
    }

    @Test
    fun test_2() {
        // given
        val tested = CounterBasedCoordinatesProvider(coordinates, initialCoordinateIndex = 1)

        // when
        val (coordinate1, consumedIndex1) = tested.getNextCoordinate()
        val (coordinate2, consumedIndex2) = tested.getNextCoordinate()
        val (coordinate3, consumedIndex3) = tested.getNextCoordinate()
        val (coordinate4, consumedIndex4) = tested.getNextCoordinate()
        val (coordinate5, consumedIndex5) = tested.getNextCoordinate()

        // then
        Assert.assertEquals(2.0, coordinate1, 0.0)
        Assert.assertEquals(1, consumedIndex1)

        Assert.assertEquals(3.0, coordinate2, 0.0)
        Assert.assertEquals(2, consumedIndex2)

        Assert.assertEquals(1.0, coordinate3, 0.0)
        Assert.assertEquals(0, consumedIndex3)

        Assert.assertEquals(2.0, coordinate4, 0.0)
        Assert.assertEquals(1, consumedIndex4)

        Assert.assertEquals(3.0, coordinate5, 0.0)
        Assert.assertEquals(2, consumedIndex5)
    }
}
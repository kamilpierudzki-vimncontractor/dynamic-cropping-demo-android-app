package tv.pluto.dynamic.cropping.android.framework

import org.junit.Assert.assertEquals
import org.junit.Test

class ListPositionCalculationTest {

    private val listPositionCalculation = ListPositionCalculation()

    @Test
    fun `calculatedPosition should return requestedIndex if it is within 1 of currentIndex`() {
        assertEquals(5, listPositionCalculation.calculatedPosition(5, 5))
        assertEquals(6, listPositionCalculation.calculatedPosition(6, 5))
        assertEquals(4, listPositionCalculation.calculatedPosition(4, 5))
    }

    @Test
    fun `calculatedPosition should return currentIndex + 1 if requestedIndex is greater by more than 1`() {
        assertEquals(6, listPositionCalculation.calculatedPosition(8, 5))
        assertEquals(10, listPositionCalculation.calculatedPosition(15, 9))
    }

    @Test
    fun `calculatedPosition should return currentIndex - 1 if requestedIndex is smaller by more than 1`() {
        assertEquals(4, listPositionCalculation.calculatedPosition(2, 5))
        assertEquals(8, listPositionCalculation.calculatedPosition(5, 9))
    }
}
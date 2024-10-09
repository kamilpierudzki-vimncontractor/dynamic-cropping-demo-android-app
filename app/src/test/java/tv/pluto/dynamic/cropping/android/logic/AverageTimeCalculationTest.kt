package tv.pluto.dynamic.cropping.android.logic

import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever

class AverageTimeCalculationTest {

    private val timestampProvider: TimestampProvider = mock()

    @Test
    fun test_1() {
        // given
        whenever(timestampProvider.timestampMs).doReturn(0L, 100L, 200L, 300L)
        val tested = AverageTimeCalculation(timestampProvider)

        // when
        val actual1 = tested.calculateAverageTimeBetweenFrames()
        val actual2 = tested.calculateAverageTimeBetweenFrames()
        val actual3 = tested.calculateAverageTimeBetweenFrames()
        val actual4 = tested.calculateAverageTimeBetweenFrames()

        // then
        Assert.assertEquals(null, actual1)
        Assert.assertEquals(100.0, actual2)
        Assert.assertEquals(100.0, actual3)
        Assert.assertEquals(100.0, actual4)
    }

    @Test
    fun test_2() {
        // given
        whenever(timestampProvider.timestampMs).doReturn(100L, 200L, 400L, 700L)
        val tested = AverageTimeCalculation(timestampProvider)

        // when
        val actual1 = tested.calculateAverageTimeBetweenFrames()
        val actual2 = tested.calculateAverageTimeBetweenFrames()
        val actual3 = tested.calculateAverageTimeBetweenFrames()
        val actual4 = tested.calculateAverageTimeBetweenFrames()

        // then
        Assert.assertEquals(null, actual1)
        Assert.assertEquals(100.0, actual2)
        Assert.assertEquals(150.0, actual3)
        Assert.assertEquals(200.0, actual4)
    }

    @Test
    fun test_3() {
        // given
        whenever(timestampProvider.timestampMs).doReturn(100L, 200L, 400L, 700L, 1100L)
        val tested = AverageTimeCalculation(timestampProvider, maxSizeOfBuffer = 4)

        // when
        val actual1 = tested.calculateAverageTimeBetweenFrames()
        val actual2 = tested.calculateAverageTimeBetweenFrames()
        val actual3 = tested.calculateAverageTimeBetweenFrames()
        val actual4 = tested.calculateAverageTimeBetweenFrames()
        val actual5 = tested.calculateAverageTimeBetweenFrames()

        // then
        Assert.assertEquals(null, actual1)
        Assert.assertEquals(100.0, actual2)
        Assert.assertEquals(150.0, actual3)
        Assert.assertEquals(200.0, actual4)
        Assert.assertEquals(300.0, actual5)
    }

    @Test
    fun test_4() {
        // given
        whenever(timestampProvider.timestampMs).doReturn(100L, 200L, 400L, 700L, 1500L)
        val tested = AverageTimeCalculation(timestampProvider, maxSizeOfBuffer = 4)

        // when
        val actual1 = tested.calculateAverageTimeBetweenFrames()
        val actual2 = tested.calculateAverageTimeBetweenFrames()
        val actual3 = tested.calculateAverageTimeBetweenFrames()
        val actual4 = tested.calculateAverageTimeBetweenFrames()
        val actual5 = tested.calculateAverageTimeBetweenFrames()

        // then
        Assert.assertEquals(null, actual1)
        Assert.assertEquals(100.0, actual2)
        Assert.assertEquals(150.0, actual3)
        Assert.assertEquals(200.0, actual4)
        Assert.assertEquals(433, actual5?.toInt())
    }
}
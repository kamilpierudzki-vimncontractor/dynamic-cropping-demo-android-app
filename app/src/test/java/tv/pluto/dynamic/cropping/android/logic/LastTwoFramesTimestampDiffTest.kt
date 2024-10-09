package tv.pluto.dynamic.cropping.android.logic

import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever

class LastTwoFramesTimestampDiffTest {

    private val timestampProvider: TimestampProvider = mock()

    private val tested = LastTwoFramesTimestampDiff(timestampProvider)

    @Test
    fun test_1() {
        // given
        whenever(timestampProvider.timestampMs).doReturn(100L, 200L, 400L, 700L, 1100L)

        // when
        val actual1 = tested.calculateDiff()
        val actual2 = tested.calculateDiff()
        val actual3 = tested.calculateDiff()
        val actual4 = tested.calculateDiff()
        val actual5 = tested.calculateDiff()

        // then
        Assert.assertEquals(null, actual1)
        Assert.assertEquals(100L, actual2)
        Assert.assertEquals(200L, actual3)
        Assert.assertEquals(300L, actual4)
        Assert.assertEquals(400L, actual5)
    }
}
package tv.pluto.dynamic.cropping.android.logic

import org.junit.Assert
import org.junit.Test

class PointsBetweenGeneratorTest {

    @Test
    fun test_1() {
        // given
        val tested = PointsBetweenGenerator()
        val start = 1.0
        val end = 4.0
        val n = 2

        // when
        val actual = tested.generate(start = start, end = end, n = n)

        // then
        Assert.assertEquals(listOf(2.0, 3.0), actual)
    }

    @Test
    fun test_2() {
        // given
        val tested = PointsBetweenGenerator()
        val start = 0.0
        val end = 1.0
        val n = 1

        // when
        val actual = tested.generate(start = start, end = end, n = n)

        // then
        Assert.assertEquals(listOf(0.5), actual)
    }

    @Test
    fun test_3() {
        // given
        val tested = PointsBetweenGenerator()
        val start = 0.0
        val end = 100.0
        val n = 2

        // when
        val actual = tested.generate(start = start, end = end, n = n)
            .map { it.toInt() }

        // then
        Assert.assertEquals(listOf(33, 66), actual)
    }
}
package tv.pluto.dynamic.cropping.android.logic

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PlayerPositionCalculationTest {

    private val playerWindowView: PlayerWindowView = mock()
    private val infiniteCoordinatesProvider: InfiniteCoordinatesProvider = mock()

    private val tested = PlayerPositionCalculation(playerWindowView, infiniteCoordinatesProvider)

    @Test
    fun test_1() {
        // given
        val theSameSize = Size(Width(140), Height(1))
        val playerWindowWidth = 40
        val rawCoordinate = 60.0

        whenever(playerWindowView.width).doReturn(playerWindowWidth)
        whenever(infiniteCoordinatesProvider.getNextCoordinate()).doReturn(rawCoordinate)
        tested.videoSizeOnSurface = theSameSize
        tested.originalVideoSize = theSameSize

        // when
        tested.calculateNextAbsoluteXPosition()

        // then
        verify(playerWindowView).updateXPosition(10f)
    }

    @Test
    fun test_2() {
        // given
        val theSameSize = Size(Width(140), Height(1))
        val playerWindowWidth = 40
        val rawCoordinate = 120.0

        whenever(playerWindowView.width).doReturn(playerWindowWidth)
        whenever(infiniteCoordinatesProvider.getNextCoordinate()).doReturn(rawCoordinate)
        tested.videoSizeOnSurface = theSameSize
        tested.originalVideoSize = theSameSize

        // when
        tested.calculateNextAbsoluteXPosition()

        // then
        verify(playerWindowView).updateXPosition(-50f)
    }

    @Test
    fun test_3() {
        // given
        val theSameSize = Size(Width(140), Height(1))
        val playerWindowWidth = 40
        val rawCoordinate = 130.0

        whenever(playerWindowView.width).doReturn(playerWindowWidth)
        whenever(infiniteCoordinatesProvider.getNextCoordinate()).doReturn(rawCoordinate)
        tested.videoSizeOnSurface = theSameSize
        tested.originalVideoSize = theSameSize

        // when
        tested.calculateNextAbsoluteXPosition()

        // then
        verify(playerWindowView).updateXPosition(-50f)
    }

    @Test
    fun test_4() {
        // given
        val theSameSize = Size(Width(140), Height(1))
        val playerWindowWidth = 40
        val rawCoordinate = 140.0

        whenever(playerWindowView.width).doReturn(playerWindowWidth)
        whenever(infiniteCoordinatesProvider.getNextCoordinate()).doReturn(rawCoordinate)
        tested.videoSizeOnSurface = theSameSize
        tested.originalVideoSize = theSameSize

        // when
        tested.calculateNextAbsoluteXPosition()

        // then
        verify(playerWindowView).updateXPosition(-50f)
    }

    @Test
    fun test_5() {
        // given
        val theSameSize = Size(Width(140), Height(1))
        val playerWindowWidth = 40
        val rawCoordinate = 20.0

        whenever(playerWindowView.width).doReturn(playerWindowWidth)
        whenever(infiniteCoordinatesProvider.getNextCoordinate()).doReturn(rawCoordinate)
        tested.videoSizeOnSurface = theSameSize
        tested.originalVideoSize = theSameSize

        // when
        tested.calculateNextAbsoluteXPosition()

        // then
        verify(playerWindowView).updateXPosition(50f)
    }

    @Test
    fun test_6() {
        // given
        val theSameSize = Size(Width(140), Height(1))
        val playerWindowWidth = 40
        val rawCoordinate = 10.0

        whenever(playerWindowView.width).doReturn(playerWindowWidth)
        whenever(infiniteCoordinatesProvider.getNextCoordinate()).doReturn(rawCoordinate)
        tested.videoSizeOnSurface = theSameSize
        tested.originalVideoSize = theSameSize

        // when
        tested.calculateNextAbsoluteXPosition()

        // then
        verify(playerWindowView).updateXPosition(50f)
    }

    @Test
    fun test_7() {
        // given
        val theSameSize = Size(Width(140), Height(1))
        val playerWindowWidth = 40
        val rawCoordinate = 0.0

        whenever(playerWindowView.width).doReturn(playerWindowWidth)
        whenever(infiniteCoordinatesProvider.getNextCoordinate()).doReturn(rawCoordinate)
        tested.videoSizeOnSurface = theSameSize
        tested.originalVideoSize = theSameSize

        // when
        tested.calculateNextAbsoluteXPosition()

        // then
        verify(playerWindowView).updateXPosition(50f)
    }

    @Test
    fun test_8() {
        // given
        val surfaceVideoSize = Size(Width(120), Height(1))
        val originalVideoSize = Size(Width(100), Height(1))
        val playerWindowWidth = 40
        val rawCoordinate = 60.0

        whenever(playerWindowView.width).doReturn(playerWindowWidth)
        whenever(infiniteCoordinatesProvider.getNextCoordinate()).doReturn(rawCoordinate)
        tested.videoSizeOnSurface = surfaceVideoSize
        tested.originalVideoSize = originalVideoSize

        // when
        tested.calculateNextAbsoluteXPosition()

        // then
        verify(playerWindowView).updateXPosition(-12f)
    }

    @Test
    fun test_9() {
        // given
        val surfaceVideoSize = Size(Width(120), Height(1))
        val originalVideoSize = Size(Width(100), Height(1))
        val playerWindowWidth = 40
        val rawCoordinate = 80.0

        whenever(playerWindowView.width).doReturn(playerWindowWidth)
        whenever(infiniteCoordinatesProvider.getNextCoordinate()).doReturn(rawCoordinate)
        tested.videoSizeOnSurface = surfaceVideoSize
        tested.originalVideoSize = originalVideoSize

        // when
        tested.calculateNextAbsoluteXPosition()

        // then
        verify(playerWindowView).updateXPosition(-36f)
    }

    @Test
    fun test_10() {
        // given
        val surfaceVideoSize = Size(Width(120), Height(1))
        val originalVideoSize = Size(Width(100), Height(1))
        val playerWindowWidth = 40
        val rawCoordinate = 90.0

        whenever(playerWindowView.width).doReturn(playerWindowWidth)
        whenever(infiniteCoordinatesProvider.getNextCoordinate()).doReturn(rawCoordinate)
        tested.videoSizeOnSurface = surfaceVideoSize
        tested.originalVideoSize = originalVideoSize

        // when
        tested.calculateNextAbsoluteXPosition()

        // then
        verify(playerWindowView).updateXPosition(-40f)
    }

    @Test
    fun test_11() {
        // given
        val surfaceVideoSize = Size(Width(120), Height(1))
        val originalVideoSize = Size(Width(100), Height(1))
        val playerWindowWidth = 40
        val rawCoordinate = 100.0

        whenever(playerWindowView.width).doReturn(playerWindowWidth)
        whenever(infiniteCoordinatesProvider.getNextCoordinate()).doReturn(rawCoordinate)
        tested.videoSizeOnSurface = surfaceVideoSize
        tested.originalVideoSize = originalVideoSize

        // when
        tested.calculateNextAbsoluteXPosition()

        // then
        verify(playerWindowView).updateXPosition(-40f)
    }

    @Test
    fun test_12() {
        // given
        val surfaceVideoSize = Size(Width(120), Height(1))
        val originalVideoSize = Size(Width(100), Height(1))
        val playerWindowWidth = 40
        val rawCoordinate = 20.0

        whenever(playerWindowView.width).doReturn(playerWindowWidth)
        whenever(infiniteCoordinatesProvider.getNextCoordinate()).doReturn(rawCoordinate)
        tested.videoSizeOnSurface = surfaceVideoSize
        tested.originalVideoSize = originalVideoSize

        // when
        tested.calculateNextAbsoluteXPosition()

        // then
        verify(playerWindowView).updateXPosition(36f)
    }

    @Test
    fun test_13() {
        // given
        val surfaceVideoSize = Size(Width(120), Height(1))
        val originalVideoSize = Size(Width(100), Height(1))
        val playerWindowWidth = 40
        val rawCoordinate = 10.0

        whenever(playerWindowView.width).doReturn(playerWindowWidth)
        whenever(infiniteCoordinatesProvider.getNextCoordinate()).doReturn(rawCoordinate)
        tested.videoSizeOnSurface = surfaceVideoSize
        tested.originalVideoSize = originalVideoSize

        // when
        tested.calculateNextAbsoluteXPosition()

        // then
        verify(playerWindowView).updateXPosition(40f)
    }

    @Test
    fun test_14() {
        // given
        val surfaceVideoSize = Size(Width(120), Height(1))
        val originalVideoSize = Size(Width(100), Height(1))
        val playerWindowWidth = 40
        val rawCoordinate = 0.0

        whenever(playerWindowView.width).doReturn(playerWindowWidth)
        whenever(infiniteCoordinatesProvider.getNextCoordinate()).doReturn(rawCoordinate)
        tested.videoSizeOnSurface = surfaceVideoSize
        tested.originalVideoSize = originalVideoSize

        // when
        tested.calculateNextAbsoluteXPosition()

        // then
        verify(playerWindowView).updateXPosition(40f)
    }

    @Test
    fun test_15() {
        // given
        val surfaceVideoSize = Size(Width(100), Height(1))
        val originalVideoSize = Size(Width(125), Height(1))
        val playerWindowWidth = 20
        val rawCoordinate = 40.0

        whenever(playerWindowView.width).doReturn(playerWindowWidth)
        whenever(infiniteCoordinatesProvider.getNextCoordinate()).doReturn(rawCoordinate)
        tested.videoSizeOnSurface = surfaceVideoSize
        tested.originalVideoSize = originalVideoSize

        // when
        tested.calculateNextAbsoluteXPosition()

        // then
        verify(playerWindowView).updateXPosition(18f)
    }

    @Test
    fun test_16() {
        // given
        val surfaceVideoSize = Size(Width(100), Height(1))
        val originalVideoSize = Size(Width(125), Height(1))
        val playerWindowWidth = 20
        val rawCoordinate = 90.0

        whenever(playerWindowView.width).doReturn(playerWindowWidth)
        whenever(infiniteCoordinatesProvider.getNextCoordinate()).doReturn(rawCoordinate)
        tested.videoSizeOnSurface = surfaceVideoSize
        tested.originalVideoSize = originalVideoSize

        // when
        tested.calculateNextAbsoluteXPosition()

        // then
        verify(playerWindowView).updateXPosition(-22f)
    }

    @Test
    fun test_17() {
        // given
        val surfaceVideoSize = Size(Width(100), Height(1))
        val originalVideoSize = Size(Width(125), Height(1))
        val playerWindowWidth = 40
        val rawCoordinate = 110.0

        whenever(playerWindowView.width).doReturn(playerWindowWidth)
        whenever(infiniteCoordinatesProvider.getNextCoordinate()).doReturn(rawCoordinate)
        tested.videoSizeOnSurface = surfaceVideoSize
        tested.originalVideoSize = originalVideoSize

        // when
        tested.calculateNextAbsoluteXPosition()

        // then
        verify(playerWindowView).updateXPosition(-30f)
    }

    @Test
    fun test_18() {
        // given
        val surfaceVideoSize = Size(Width(100), Height(1))
        val originalVideoSize = Size(Width(125), Height(1))
        val playerWindowWidth = 40
        val rawCoordinate = 125.0

        whenever(playerWindowView.width).doReturn(playerWindowWidth)
        whenever(infiniteCoordinatesProvider.getNextCoordinate()).doReturn(rawCoordinate)
        tested.videoSizeOnSurface = surfaceVideoSize
        tested.originalVideoSize = originalVideoSize

        // when
        tested.calculateNextAbsoluteXPosition()

        // then
        verify(playerWindowView).updateXPosition(-30f)
    }

    @Test
    fun test_19() {
        // given
        val surfaceVideoSize = Size(Width(100), Height(1))
        val originalVideoSize = Size(Width(125), Height(1))
        val playerWindowWidth = 40
        val rawCoordinate = 40.0

        whenever(playerWindowView.width).doReturn(playerWindowWidth)
        whenever(infiniteCoordinatesProvider.getNextCoordinate()).doReturn(rawCoordinate)
        tested.videoSizeOnSurface = surfaceVideoSize
        tested.originalVideoSize = originalVideoSize

        // when
        tested.calculateNextAbsoluteXPosition()

        // then
        verify(playerWindowView).updateXPosition(18f)
    }

    @Test
    fun test_20() {
        // given
        val surfaceVideoSize = Size(Width(100), Height(1))
        val originalVideoSize = Size(Width(125), Height(1))
        val playerWindowWidth = 40
        val rawCoordinate = 20.0

        whenever(playerWindowView.width).doReturn(playerWindowWidth)
        whenever(infiniteCoordinatesProvider.getNextCoordinate()).doReturn(rawCoordinate)
        tested.videoSizeOnSurface = surfaceVideoSize
        tested.originalVideoSize = originalVideoSize

        // when
        tested.calculateNextAbsoluteXPosition()

        // then
        verify(playerWindowView).updateXPosition(30f)
    }

    @Test
    fun test_21() {
        // given
        val surfaceVideoSize = Size(Width(100), Height(1))
        val originalVideoSize = Size(Width(125), Height(1))
        val playerWindowWidth = 40
        val rawCoordinate = 0.0

        whenever(playerWindowView.width).doReturn(playerWindowWidth)
        whenever(infiniteCoordinatesProvider.getNextCoordinate()).doReturn(rawCoordinate)
        tested.videoSizeOnSurface = surfaceVideoSize
        tested.originalVideoSize = originalVideoSize

        // when
        tested.calculateNextAbsoluteXPosition()

        // then
        verify(playerWindowView).updateXPosition(30f)
    }
}
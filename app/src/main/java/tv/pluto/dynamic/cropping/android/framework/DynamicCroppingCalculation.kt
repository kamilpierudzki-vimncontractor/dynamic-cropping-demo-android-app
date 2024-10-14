package tv.pluto.dynamic.cropping.android.framework

import android.graphics.Matrix
import android.view.TextureView
import tv.pluto.dynamic.cropping.android.logic.CalculateNewTextureSize
import tv.pluto.dynamic.cropping.android.logic.InfiniteCoordinatesProvider
import tv.pluto.dynamic.cropping.android.logic.VideoResolution
import tv.pluto.dynamic.cropping.android.logic.TextureSize
import tv.pluto.dynamic.cropping.android.logic.TextureOffsetCalculation

class DynamicCroppingCalculation(
    private val infiniteCoordinatesProvider: InfiniteCoordinatesProvider,
    private val textureView: TextureView,
    private val textureOffsetCalculation: TextureOffsetCalculation,
    private val calculateNewTextureSize: CalculateNewTextureSize,
    private val viewSizeProvider: ViewSizeProvider,
) {

    private var scaleMatrix: Matrix? = null
    private var textureSize: TextureSize? = null
    private var videoResolution: VideoResolution? = null

    fun applyScaleToTextureAccordingToVideoSize(videoResolution: VideoResolution) {
        this.videoResolution = videoResolution
        val newTextureSize = calculateNewTextureSize.calculate(
            videoResolution = videoResolution,
            textureViewSize = viewSizeProvider.getSize(textureView),
        ).also {
            this.textureSize = it
        }
        this.scaleMatrix = scale(newTextureSize).also { scale ->
            textureView.setTransform(scale)
        }
    }

    fun onNewFrame() {
        val coordinate = infiniteCoordinatesProvider.getNextCoordinate()
        val txSize = textureSize
        val res = videoResolution
        if (txSize != null && res != null) {
            val newTranslationX = textureOffsetCalculation.calculateXAxisAbsoluteOffset(
                coordinate = coordinate,
                videoResolution = res,
                textureViewWidth = textureView.width,
                textureSize = txSize,
            )
            moveTexture(newTranslationX.toFloat())
        }
    }

    private fun moveTexture(translationX: Float) {
        val matrix = scaleMatrix
        if (matrix != null) {
            val translationMatrix = Matrix(matrix)
            translationMatrix.postTranslate(translationX, 0f)
            textureView.setTransform(translationMatrix)
        }
    }

    private fun scale(targetTextureSize: TextureSize): Matrix {
        val scaleX = (1.0 * targetTextureSize.value.width.value) / textureView.width
        val scaleY = (1.0 * targetTextureSize.value.height.value) / textureView.height
        return Matrix().apply {
            setScale(scaleX.toFloat(), scaleY.toFloat())
        }
    }
}
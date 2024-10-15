package tv.pluto.dynamic.cropping.android.framework

import android.graphics.Matrix
import android.view.TextureView
import android.view.View
import tv.pluto.dynamic.cropping.android.logic.CalculateNewTextureSize
import tv.pluto.dynamic.cropping.android.logic.CalculateTextureXAxisAbsoluteOffset
import tv.pluto.dynamic.cropping.android.logic.CoordinatesProvider
import tv.pluto.dynamic.cropping.android.logic.Height
import tv.pluto.dynamic.cropping.android.logic.Size
import tv.pluto.dynamic.cropping.android.logic.TextureSize
import tv.pluto.dynamic.cropping.android.logic.VideoResolution
import tv.pluto.dynamic.cropping.android.logic.Width

class DynamicCroppingCalculation(
    private val coordinatesProvider: CoordinatesProvider,
    private val textureView: TextureView,
    private val calculateTextureXAxisAbsoluteOffset: CalculateTextureXAxisAbsoluteOffset,
    private val calculateNewTextureSize: CalculateNewTextureSize,
) {

    private var scaleMatrix: Matrix? = null
    private var textureSize: TextureSize? = null
    private var videoResolution: VideoResolution? = null

    fun applyInitialSetupOfTexture(videoResolution: VideoResolution) {
        this.videoResolution = videoResolution
        val newTextureSize = calculateNewTextureSize.calculate(
            videoResolution = videoResolution,
            textureViewSize = textureView.getSize(),
        ).also {
            this.textureSize = it
        }
        this.scaleMatrix = scale(newTextureSize).also { scale ->
            textureView.setTransform(scale)
        }
        moveTextureToCenter()
    }

    private fun moveTextureToCenter() {
        textureSize?.let { txSize ->
            val centerOfTexture = (txSize.value.width.value / 2.0)
            val textureAbsolutePositionMovedToLeft = centerOfTexture * (-1.0)
            moveTexture(textureAbsolutePositionMovedToLeft.toFloat())
        }
    }

    fun onNewFrame(frame: Int) {
        val coordinate = coordinatesProvider.getCoordinate(frame)
        val txSize = textureSize
        val res = videoResolution
        if (txSize != null && res != null) {
            val absoluteOffset = calculateTextureXAxisAbsoluteOffset.calculated(
                coordinate = coordinate,
                videoResolution = res,
                textureViewWidth = textureView.width,
                textureSize = txSize,
            )
            moveTexture(absoluteOffset.toFloat())
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

private fun View.getSize() = Size(Width(width), Height(height))
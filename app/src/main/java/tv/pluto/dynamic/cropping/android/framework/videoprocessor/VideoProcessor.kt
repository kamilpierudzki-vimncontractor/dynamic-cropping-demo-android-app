package tv.pluto.dynamic.cropping.android.framework.videoprocessor

import android.graphics.Matrix
import android.graphics.SurfaceTexture
import android.view.Surface
import android.view.TextureView
import com.google.android.exoplayer2.ExoPlayer
import tv.pluto.dynamic.cropping.android.logic.Height
import tv.pluto.dynamic.cropping.android.logic.Size
import tv.pluto.dynamic.cropping.android.logic.Width

class VideoProcessor(
    private val exoPlayer: ExoPlayer,
    private val textureView: TextureView,
) : TextureView.SurfaceTextureListener {

    private var scaleMatrix: Matrix? = null
    var videoSizeOnSurface: Size? = null

    override fun onSurfaceTextureAvailable(surfaceTexture: SurfaceTexture, width: Int, height: Int) {
        videoSizeOnSurface = Size(Width(width), Height(height)).also {
            android.util.Log.d("test321", "onSurfaceTextureAvailable, $it")
        }
        val surface = Surface(surfaceTexture)
        exoPlayer.setVideoSurface(surface)
    }

    override fun onSurfaceTextureSizeChanged(surfaceTexture: SurfaceTexture, width: Int, height: Int) {
    }

    override fun onSurfaceTextureDestroyed(surfaceTexture: SurfaceTexture): Boolean {
        return true
    }

    override fun onSurfaceTextureUpdated(v: SurfaceTexture) {
    }

    fun onVideoSizeChanged(size: Size) {
        android.util.Log.d("test321", "onVideoSizeChanged, size: $size")
        scaleMatrix = scale(size).also { scale ->
            textureView.setTransform(scale)
        }
    }

    fun onAbsoluteXPositionChanged(newAbsoluteXPos: Double) {
        move(newAbsoluteXPos.toFloat())
    }

    private fun move(translationX: Float) {
        val matrix = scaleMatrix
        if (matrix != null) {
            val translationMatrix = Matrix(matrix)
            translationMatrix.postTranslate(translationX, 0f)
            textureView.setTransform(translationMatrix)
        }
    }

    private fun scale(originalVideoSize: Size): Matrix {
        val aspectRatio = (1.0 * originalVideoSize.width.value) / originalVideoSize.height.value
        val newSurfaceWidth = textureView.height * aspectRatio

        val scaleX = newSurfaceWidth / textureView.width
        return Matrix().apply {
            setScale(scaleX.toFloat(), 1f)
        }
    }
}
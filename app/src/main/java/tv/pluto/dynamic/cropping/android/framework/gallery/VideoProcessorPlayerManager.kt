package tv.pluto.dynamic.cropping.android.framework.gallery

import android.app.Activity
import android.graphics.SurfaceTexture
import android.view.Surface
import android.view.TextureView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.video.VideoSize
import tv.pluto.dynamic.cropping.android.framework.TextureCalculation
import tv.pluto.dynamic.cropping.android.framework.ViewSizeProvider
import tv.pluto.dynamic.cropping.android.logic.CalculateCoordinateOffset
import tv.pluto.dynamic.cropping.android.logic.CalculateNewTextureSize
import tv.pluto.dynamic.cropping.android.logic.Height
import tv.pluto.dynamic.cropping.android.logic.InfiniteCoordinatesProvider
import tv.pluto.dynamic.cropping.android.logic.ScaleCoordinate
import tv.pluto.dynamic.cropping.android.logic.Size
import tv.pluto.dynamic.cropping.android.logic.VideoResolution
import tv.pluto.dynamic.cropping.android.logic.Width
import tv.pluto.dynamic.cropping.android.logic.WindowCalculation

class VideoProcessorPlayerManager(
    private val activity: Activity,
) : DefaultLifecycleObserver {

    private var exoPlayer: ExoPlayer? = null

    fun setPlayerView(
        textureView: TextureView,
        coordinates: DoubleArray,
    ) {
        ExoPlayer.Builder(activity).build().also { exoPlayer ->
            this.exoPlayer = exoPlayer
            exoPlayer.repeatMode = Player.REPEAT_MODE_ALL
            exoPlayer.setVideoTextureView(textureView)

            val textureCalculation = TextureCalculation(
                InfiniteCoordinatesProvider(coordinates),
                textureView,
                WindowCalculation(ScaleCoordinate(), CalculateCoordinateOffset()),
                CalculateNewTextureSize(),
                ViewSizeProvider(),
            )

            exoPlayer.setVideoFrameMetadataListener { _, _, _, _ ->
                textureCalculation.onNewFrame()
            }

            exoPlayer.addListener(object : Player.Listener {
                override fun onVideoSizeChanged(videoSize: VideoSize) {
                    val resolution = VideoResolution(Size(Width(videoSize.width), Height(videoSize.height)))
                    textureCalculation.applyScaleToTextureAccordingToVideoSize(resolution)
                }
            })

            textureView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
                override fun onSurfaceTextureAvailable(surfaceTexture: SurfaceTexture, p1: Int, p2: Int) {
                    val surface = Surface(surfaceTexture)
                    exoPlayer.setVideoSurface(surface)
                }

                override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, p1: Int, p2: Int) {
                }

                override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean = true

                override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {
                }
            }
        }
    }

    fun setMediaItemAndPlay(mediaItem: MediaItem) {
        exoPlayer?.apply {
            setMediaItem(mediaItem)
            prepare()
            play()
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        exoPlayer?.pause()
    }

    override fun onStart(owner: LifecycleOwner) {
        exoPlayer?.also {
            if (!it.isPlaying) {
                it.play()
            }
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        destroy()
    }

    fun destroy() {
        exoPlayer?.release()
        exoPlayer = null
    }
}
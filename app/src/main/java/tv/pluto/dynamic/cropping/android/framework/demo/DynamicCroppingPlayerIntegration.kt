package tv.pluto.dynamic.cropping.android.framework.demo

import android.app.Activity
import android.graphics.SurfaceTexture
import android.view.Surface
import android.view.TextureView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.google.android.exoplayer2.video.VideoFrameMetadataListener
import com.google.android.exoplayer2.video.VideoSize
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import tv.pluto.dynamic.cropping.android.framework.DynamicCroppingCalculation
import tv.pluto.dynamic.cropping.android.framework.Video
import tv.pluto.dynamic.cropping.android.framework.ViewSizeProvider
import tv.pluto.dynamic.cropping.android.logic.CalculateNewTextureSize
import tv.pluto.dynamic.cropping.android.logic.Height
import tv.pluto.dynamic.cropping.android.logic.InfiniteCoordinatesProvider
import tv.pluto.dynamic.cropping.android.logic.OffScreenOffsetCalculation
import tv.pluto.dynamic.cropping.android.logic.ScaleCoordinate
import tv.pluto.dynamic.cropping.android.logic.Size
import tv.pluto.dynamic.cropping.android.logic.TextureOffsetCalculation
import tv.pluto.dynamic.cropping.android.logic.VideoResolution
import tv.pluto.dynamic.cropping.android.logic.Width

private sealed interface PlaybackState {
    data object PausedForegrounded : PlaybackState
    data object PausedBackgrounded : PlaybackState
    data object PlayingForegrounded : PlaybackState
    data object PlayingBackgrounded : PlaybackState
}

class DynamicCroppingPlayerIntegration(
    private val activity: Activity,
    private val lifecycleCoroutineScope: LifecycleCoroutineScope,
    private val mainDispatcher: CoroutineDispatcher,
    val video: Video,
) : DefaultLifecycleObserver {

    private var exoPlayer: ExoPlayer? = null
    private var dynamicCroppingCalculation: DynamicCroppingCalculation? = null
    private var textureView: TextureView? = null
    private var playbackState: PlaybackState = PlaybackState.PausedForegrounded

    private val videoFrameMetadataListener = VideoFrameMetadataListener { _, _, _, _ ->
        lifecycleCoroutineScope.launch(mainDispatcher) {
            val calc = dynamicCroppingCalculation
            val txtView = textureView
            if (calc != null && txtView != null) {
                calc.onNewFrame(txtView)
            }
        }
    }

    private val playerListener = object : Player.Listener {
        override fun onVideoSizeChanged(videoSize: VideoSize) {
            lifecycleCoroutineScope.launch(mainDispatcher) {
                val calc = dynamicCroppingCalculation
                val txtView = textureView
                if (calc != null && txtView != null) {
                    val resolution = VideoResolution(Size(Width(videoSize.width), Height(videoSize.height)))
                    calc.applyInitialSetupOfTexture(txtView, resolution)
                }
            }
        }
    }

    private val surfaceTextureListener = object : TextureView.SurfaceTextureListener {
        override fun onSurfaceTextureAvailable(surfaceTexture: SurfaceTexture, p1: Int, p2: Int) {
            lifecycleCoroutineScope.launch(mainDispatcher) {
                val surface = Surface(surfaceTexture)
                exoPlayer?.setVideoSurface(surface)
            }
        }

        override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, p1: Int, p2: Int) {
        }

        override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean = true

        override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {
        }
    }

    fun onUiCreated(textureView: TextureView) {
        this.textureView = textureView
        exoPlayer?.setVideoTextureView(textureView)
        textureView.surfaceTextureListener = surfaceTextureListener
    }

    fun onUiReleased() {
        pause()
        exoPlayer?.setVideoTextureView(null)
        textureView?.surfaceTextureListener = null
    }

    fun pause() {
        android.util.Log.d("test123", "pause(), ${video.title}")
        exoPlayer?.pause()
        playbackState = PlaybackState.PausedForegrounded
    }

    fun play() {
        android.util.Log.d("test123", "play(), ${video.title}")
        exoPlayer?.play()
        playbackState = PlaybackState.PlayingForegrounded
    }

    override fun onCreate(owner: LifecycleOwner) {
        initializeExoPlayer()
        setMediaItem(createMediaItem())
    }

    override fun onStart(owner: LifecycleOwner) {
        when (playbackState) {
            PlaybackState.PlayingBackgrounded -> {
                exoPlayer?.play()
                playbackState = PlaybackState.PlayingForegrounded
            }

            PlaybackState.PausedBackgrounded -> {
                playbackState = PlaybackState.PausedForegrounded
            }

            else -> {}
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        exoPlayer?.let { player ->
            when (playbackState) {
                PlaybackState.PausedForegrounded -> {
                    playbackState = PlaybackState.PausedBackgrounded
                }

                PlaybackState.PlayingForegrounded -> {
                    player.pause()
                    playbackState = PlaybackState.PlayingBackgrounded
                }

                else -> {}
            }
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        exoPlayer?.release()
        exoPlayer = null
    }

    private fun initializeExoPlayer() {
        ExoPlayer.Builder(activity).build().also { exoPlayer ->
            this.exoPlayer = exoPlayer
            exoPlayer.repeatMode = Player.REPEAT_MODE_ALL

            dynamicCroppingCalculation = DynamicCroppingCalculation(
                InfiniteCoordinatesProvider(video.coordinates()),
                TextureOffsetCalculation(ScaleCoordinate(), OffScreenOffsetCalculation()),
                CalculateNewTextureSize(),
                ViewSizeProvider(),
            )

            exoPlayer.setVideoFrameMetadataListener(videoFrameMetadataListener)
            exoPlayer.addListener(playerListener)
        }
    }

    private fun setMediaItem(mediaItem: MediaItem) {
        exoPlayer?.apply {
            setMediaItem(mediaItem)
            prepare()
        }
    }

    private fun createMediaItem(): MediaItem {
        val uri = RawResourceDataSource.buildRawResourceUri(video.videoResId)
        return MediaItem.fromUri(uri)
    }
}
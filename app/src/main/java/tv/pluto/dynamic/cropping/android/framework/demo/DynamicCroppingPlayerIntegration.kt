package tv.pluto.dynamic.cropping.android.framework.demo

import android.content.Context
import android.graphics.SurfaceTexture
import android.view.Surface
import android.view.TextureView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.google.android.exoplayer2.video.VideoSize
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import tv.pluto.dynamic.cropping.android.framework.DynamicCroppingCalculation
import tv.pluto.dynamic.cropping.android.framework.Metadata
import tv.pluto.dynamic.cropping.android.logic.CalculateNewTextureSize
import tv.pluto.dynamic.cropping.android.logic.CalculateOffScreenOffset
import tv.pluto.dynamic.cropping.android.logic.CalculateTextureXAxisAbsoluteOffset
import tv.pluto.dynamic.cropping.android.logic.Height
import tv.pluto.dynamic.cropping.android.logic.InfiniteCoordinatesProvider
import tv.pluto.dynamic.cropping.android.logic.ScaleCoordinate
import tv.pluto.dynamic.cropping.android.logic.Size
import tv.pluto.dynamic.cropping.android.logic.VideoResolution
import tv.pluto.dynamic.cropping.android.logic.Width

private sealed interface PlaybackState {
    data object PausedForegrounded : PlaybackState
    data object PausedBackgrounded : PlaybackState
    data object PlayingForegrounded : PlaybackState
    data object PlayingBackgrounded : PlaybackState
}

class DynamicCroppingPlayerIntegration(
    private val lifecycleOwner: LifecycleOwner,
    private val context: Context,
    private val mainDispatcher: CoroutineDispatcher,
    private val textureView: TextureView,
    private val staticMetadata: Metadata,
    private val initialPlaybackPositionMs: Long,
    val onPlaybackPositionChanged: (Long) -> Unit,
) : DefaultLifecycleObserver {

    private var exoPlayer: ExoPlayer? = null
    private var playbackState: PlaybackState = PlaybackState.PausedForegrounded

    init {
        lifecycleOwner.lifecycle.addObserver(this)
        createExoPlayer()
        setMediaItem(createMediaItem())
    }

    fun destroy() {
        lifecycleOwner.lifecycle.removeObserver(this)
        exoPlayer?.release()
        exoPlayer = null
    }

    fun pause() {
        android.util.Log.d("test123", "\tpause, ${staticMetadata.title}")
        exoPlayer?.pause()
        playbackState = PlaybackState.PausedForegrounded
    }

    fun play() {
        android.util.Log.d("test123", "\tplay, ${staticMetadata.title}")
        exoPlayer?.play()
        playbackState = PlaybackState.PlayingForegrounded
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
        destroy()
    }

    private fun createExoPlayer() {
        ExoPlayer.Builder(context).build().also { exoPlayer ->
            this.exoPlayer = exoPlayer
            exoPlayer.repeatMode = Player.REPEAT_MODE_ALL
            exoPlayer.setVideoTextureView(textureView)

            val dynamicCroppingCalculation = DynamicCroppingCalculation(
                InfiniteCoordinatesProvider(staticMetadata.coordinates()),
                textureView,
                CalculateTextureXAxisAbsoluteOffset(ScaleCoordinate(), CalculateOffScreenOffset()),
                CalculateNewTextureSize(),
            )

            exoPlayer.setVideoFrameMetadataListener { _, _, _, _ ->
                lifecycleOwner.lifecycleScope.launch(mainDispatcher) {
                    onPlaybackPositionChanged(exoPlayer.currentPosition)
                    dynamicCroppingCalculation.onNewFrame()
                }
            }

            exoPlayer.addListener(object : Player.Listener {
                override fun onVideoSizeChanged(videoSize: VideoSize) {
                    lifecycleOwner.lifecycleScope.launch(mainDispatcher) {
                        val resolution = VideoResolution(Size(Width(videoSize.width), Height(videoSize.height)))
                        dynamicCroppingCalculation.applyInitialSetupOfTexture(resolution)
                    }
                }
            })

            textureView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
                override fun onSurfaceTextureAvailable(surfaceTexture: SurfaceTexture, p1: Int, p2: Int) {
                    lifecycleOwner.lifecycleScope.launch(mainDispatcher) {
                        val surface = Surface(surfaceTexture)
                        exoPlayer.setVideoSurface(surface)
                    }
                }

                override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, p1: Int, p2: Int) {
                }

                override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean = true

                override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {
                }
            }

            android.util.Log.d("test321", "seekTo: $initialPlaybackPositionMs")
            exoPlayer.seekTo(initialPlaybackPositionMs)
        }
    }

    private fun setMediaItem(mediaItem: MediaItem) {
        exoPlayer?.apply {
            setMediaItem(mediaItem)
            prepare()
        }
    }

    private fun createMediaItem(): MediaItem {
        val uri = RawResourceDataSource.buildRawResourceUri(staticMetadata.videoResId)
        return MediaItem.fromUri(uri)
    }
}
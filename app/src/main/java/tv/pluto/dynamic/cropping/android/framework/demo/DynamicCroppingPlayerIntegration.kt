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

    private var playbackState: PlaybackState = PlaybackState.PausedForegrounded
    private var playOnlyOneFrame: Boolean = /*true*/false

    fun initializeAndStartPlayback(textureView: TextureView) {
        setPlayerView(textureView)
        setMediaItem(createMediaItem())
    }

    fun pause() {
        exoPlayer?.let { player ->
            player.pause()
            playbackState = PlaybackState.PausedForegrounded
        }
    }

    fun play(playOnlyOneFrame: Boolean = false) {
        exoPlayer?.let { player ->
            this.playOnlyOneFrame = playOnlyOneFrame
            player.play()
            playbackState = PlaybackState.PlayingForegrounded
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        exoPlayer?.let { player ->
            when (playbackState) {
                PlaybackState.PlayingBackgrounded -> {
                    player.play()
                    playbackState = PlaybackState.PlayingForegrounded
                }

                PlaybackState.PausedBackgrounded -> {
                    playbackState = PlaybackState.PausedForegrounded
                }

                else -> {}
            }
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

    private fun setPlayerView(textureView: TextureView) {
        ExoPlayer.Builder(activity).build().also { exoPlayer ->
            this.exoPlayer = exoPlayer
            exoPlayer.repeatMode = Player.REPEAT_MODE_ALL
            exoPlayer.setVideoTextureView(textureView)

            val dynamicCroppingCalculation = DynamicCroppingCalculation(
                InfiniteCoordinatesProvider(video.coordinates()),
                textureView,
                TextureOffsetCalculation(ScaleCoordinate(), OffScreenOffsetCalculation()),
                CalculateNewTextureSize(),
                ViewSizeProvider(),
            )

            exoPlayer.setVideoFrameMetadataListener { _, _, _, _ ->
                lifecycleCoroutineScope.launch(mainDispatcher) {
                    dynamicCroppingCalculation.onNewFrame()
                    if (playOnlyOneFrame) {
                        playOnlyOneFrame = false
                        pause()
                    }
                }
            }

            exoPlayer.addListener(object : Player.Listener {
                override fun onVideoSizeChanged(videoSize: VideoSize) {
                    lifecycleCoroutineScope.launch(mainDispatcher) {
                        val resolution = VideoResolution(Size(Width(videoSize.width), Height(videoSize.height)))
                        dynamicCroppingCalculation.applyInitialSetupOfTexture(resolution)
                    }
                }
            })

            textureView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
                override fun onSurfaceTextureAvailable(surfaceTexture: SurfaceTexture, p1: Int, p2: Int) {
                    lifecycleCoroutineScope.launch(mainDispatcher) {
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
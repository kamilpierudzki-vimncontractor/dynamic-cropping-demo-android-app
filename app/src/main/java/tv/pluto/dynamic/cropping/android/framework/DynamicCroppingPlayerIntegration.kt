package tv.pluto.dynamic.cropping.android.framework

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
import com.google.android.exoplayer2.Tracks
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.exoplayer2.video.VideoSize
import kotlin.math.roundToInt
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import tv.pluto.dynamic.cropping.android.logic.CalculateNewTextureSize
import tv.pluto.dynamic.cropping.android.logic.CalculateOffScreenOffset
import tv.pluto.dynamic.cropping.android.logic.CalculateTextureXAxisAbsoluteOffset
import tv.pluto.dynamic.cropping.android.logic.Height
import tv.pluto.dynamic.cropping.android.logic.ScaleCoordinate
import tv.pluto.dynamic.cropping.android.logic.VideoResolution
import tv.pluto.dynamic.cropping.android.logic.Width

class DynamicCroppingPlayerIntegration(
    private val lifecycleOwner: LifecycleOwner,
    private val context: Context,
    private val mainDispatcher: CoroutineDispatcher,
    private val textureView: TextureView,
    private val video: Video,
    private val initialPlaybackPositionMs: Long,
    private val onPlaybackPositionChanged: (Long) -> Unit,
    private val onVideoEnded: () -> Unit,
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
        if (playbackState != PlaybackState.PausedForegrounded) {
            playbackState = PlaybackState.PausedForegrounded
            exoPlayer?.pause()
        }
    }

    fun play(startFromPositionMs: Long) {
        if (playbackState != PlaybackState.PlayingForegrounded) {
            playbackState = PlaybackState.PlayingForegrounded
            exoPlayer?.apply {
                seekTo(startFromPositionMs)
                play()
            }
        }
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
            exoPlayer.setVideoTextureView(textureView)

            val dynamicCroppingCalculation = DynamicCroppingCalculation(
                textureView,
                CalculateTextureXAxisAbsoluteOffset(ScaleCoordinate(), CalculateOffScreenOffset()),
                CalculateNewTextureSize(),
            )

            exoPlayer.setVideoFrameMetadataListener { presentationTimeUs, _, format, _ ->
                lifecycleOwner.lifecycleScope.launch(mainDispatcher) {
                    onPlaybackPositionChanged(exoPlayer.currentPosition)
                    val calculatedCoordinateIndex = ((presentationTimeUs / 1_000_000.0) * format.frameRate).roundToInt()
                    val coordinate = video.coordinates.getOrNull(calculatedCoordinateIndex) ?: return@launch
                    dynamicCroppingCalculation.onNewFrame(coordinate)
                }
            }

            exoPlayer.addListener(object : Player.Listener {
                override fun onVideoSizeChanged(videoSize: VideoSize) {
                    lifecycleOwner.lifecycleScope.launch(mainDispatcher) {
                        val resolution = VideoResolution(Width(videoSize.width), Height(videoSize.height))
                        dynamicCroppingCalculation.applyInitialSetupOfTexture(resolution)
                    }
                }

                override fun onTracksChanged(tracks: Tracks) {
                    lifecycleOwner.lifecycleScope.launch(mainDispatcher) {
                        val trackGroups = tracks.groups
                        for (i in 0 until trackGroups.size) {
                            val trackGroup = trackGroups[i]
                            for (j in 0 until trackGroup.length) {
                                val format = trackGroup.getTrackFormat(j)
                                if (MimeTypes.isVideo(format.sampleMimeType)) {
                                    val resolution = VideoResolution(Width(format.width), Height(format.height))
                                    dynamicCroppingCalculation.applyInitialSetupOfTexture(resolution)
                                }
                            }
                        }
                    }
                }

                override fun onPlaybackStateChanged(playbackState: Int) {
                    if (playbackState == Player.STATE_ENDED) {
                        lifecycleOwner.lifecycleScope.launch(mainDispatcher) {
                            exoPlayer.seekTo(0)
                            onVideoEnded()
                        }
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
        }
    }

    private fun setMediaItem(mediaItem: MediaItem) {
        exoPlayer?.apply {
            setMediaItem(mediaItem)
            prepare()
            seekTo(initialPlaybackPositionMs)
        }
    }

    private fun createMediaItem(): MediaItem {
        val uri = RawResourceDataSource.buildRawResourceUri(video.videoResId)
        return MediaItem.fromUri(uri)
    }
}
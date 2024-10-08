package tv.pluto.dynamic.cropping.android.framework.demo

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.google.android.exoplayer2.video.VideoSize
import tv.pluto.dynamic.cropping.android.framework.PlayerWindowViewWrapperImpl
import tv.pluto.dynamic.cropping.android.logic.Height
import tv.pluto.dynamic.cropping.android.logic.InfiniteCoordinatesProvider
import tv.pluto.dynamic.cropping.android.logic.PlayerPositionCalculation
import tv.pluto.dynamic.cropping.android.logic.Size
import tv.pluto.dynamic.cropping.android.logic.Width

private sealed interface PlaybackState {
    data object PausedForegrounded : PlaybackState
    data object PausedBackgrounded : PlaybackState
    data object PlayingForegrounded : PlaybackState
    data object PlayingBackgrounded : PlaybackState
}

class ExoPlayerManagerV2Impl(
    private val activity: Activity,
    private val videoResourceId: Int,
    override val videoName: String,
    private val coordinates: () -> DoubleArray
) : ExoPlayerManagerV2 {

    private var exoPlayer: ExoPlayer? = null
    private var playerPositionCalculation: PlayerPositionCalculation? = null
    private var isPlaying: Boolean = false
    private var playbackState: PlaybackState = PlaybackState.PausedForegrounded

    override fun initializeAndStartPlayback(playerView: StyledPlayerView) {
        setPlayerView(playerView)
        setMediaItem(createMediaItem())
    }

    override fun pause() {
        exoPlayer?.let { player ->
            player.pause()
            playbackState = PlaybackState.PausedForegrounded
        }
    }

    override fun play() {
        exoPlayer?.let { player ->
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
        destroy()
    }

    override fun destroy() {
        exoPlayer?.release()
        exoPlayer = null
        playerPositionCalculation = null
    }

    private fun setPlayerView(playerView: StyledPlayerView) {
        ExoPlayer.Builder(activity).build().also { exoPlayer ->
            this.exoPlayer = exoPlayer
            exoPlayer.repeatMode = Player.REPEAT_MODE_ALL
            playerView.player = exoPlayer
            playerView.useController = false

            playerPositionCalculation = PlayerPositionCalculation(
                PlayerWindowViewWrapperImpl(playerView),
                InfiniteCoordinatesProvider(coordinates()),
            )

            exoPlayer.setVideoFrameMetadataListener { _, _, _, _ ->
                playerPositionCalculation?.onNewFrame()
            }

            exoPlayer.addListener(object : Player.Listener {
                override fun onSurfaceSizeChanged(width: Int, height: Int) {
                    playerPositionCalculation?.videoSizeOnSurface = Size(Width(width), Height(height))
                }

                override fun onVideoSizeChanged(videoSize: VideoSize) {
                    playerPositionCalculation?.originalVideoSize = Size(
                        Width(videoSize.width),
                        Height(videoSize.height),
                    )
                }
            })
        }
    }

    private fun setMediaItem(mediaItem: MediaItem) {
        exoPlayer?.apply {
            setMediaItem(mediaItem)
            prepare()
        }
    }

    private fun createMediaItem(): MediaItem {
        val uri = RawResourceDataSource.buildRawResourceUri(videoResourceId)
        return MediaItem.fromUri(uri)
    }
}
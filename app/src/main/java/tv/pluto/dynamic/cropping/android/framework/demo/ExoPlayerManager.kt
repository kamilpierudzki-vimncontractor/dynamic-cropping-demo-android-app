package tv.pluto.dynamic.cropping.android.framework.demo

import android.app.Activity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.google.android.exoplayer2.video.VideoSize
import tv.pluto.dynamic.cropping.android.framework.Manipulation
import tv.pluto.dynamic.cropping.android.framework.Video
import tv.pluto.dynamic.cropping.android.framework.window.PlayerWindowViewManipulationFactory
import tv.pluto.dynamic.cropping.android.logic.Height
import tv.pluto.dynamic.cropping.android.logic.Size
import tv.pluto.dynamic.cropping.android.logic.Width

private sealed interface PlaybackState {
    data object PausedForegrounded : PlaybackState
    data object PausedBackgrounded : PlaybackState
    data object PlayingForegrounded : PlaybackState
    data object PlayingBackgrounded : PlaybackState
}

class ExoPlayerManager(
    private val activity: Activity,
    val video: Video,
) : DefaultLifecycleObserver {

    private var exoPlayer: ExoPlayer? = null
    private var manipulation: Manipulation? = null

    private var playbackState: PlaybackState = PlaybackState.PausedForegrounded

    fun initializeAndStartPlayback(playerView: StyledPlayerView) {
        setPlayerView(playerView)
        setMediaItem(createMediaItem())
    }

    fun pause() {
        exoPlayer?.let { player ->
            player.pause()
            playbackState = PlaybackState.PausedForegrounded
        }
        manipulation?.cancelOngoingOperations()
    }

    fun play() {
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
        manipulation?.cancelOngoingOperations()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        exoPlayer?.release()
        exoPlayer = null
        manipulation = null
    }

    private fun setPlayerView(playerView: StyledPlayerView) {
        ExoPlayer.Builder(activity).build().also { exoPlayer ->
            this.exoPlayer = exoPlayer
            exoPlayer.repeatMode = Player.REPEAT_MODE_ALL
            playerView.player = exoPlayer
            playerView.useController = false

            manipulation = Manipulation(
                playerView = playerView,
                coordinates = video.coordinates(),
                playerWindowViewManipulationFactory = PlayerWindowViewManipulationFactory(),
            )

            exoPlayer.setVideoFrameMetadataListener { _, _, _, _ ->
//                manipulation?.onNewFrame()
            }
            exoPlayer.addListener(object : Player.Listener {
                override fun onSurfaceSizeChanged(width: Int, height: Int) {
                    manipulation?.onSurfaceSizeChanged(Size(Width(width), Height(height)))
                }

                override fun onVideoSizeChanged(videoSize: VideoSize) {
                    manipulation?.onVideoSizeChanged(Size(Width(videoSize.width), Height(videoSize.height)))
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
        val uri = RawResourceDataSource.buildRawResourceUri(video.videoResId)
        return MediaItem.fromUri(uri)
    }
}
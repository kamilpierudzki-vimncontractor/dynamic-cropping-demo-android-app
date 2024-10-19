package tv.pluto.dynamic.cropping.android.framework.demo

import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import tv.pluto.dynamic.cropping.android.framework.Video
import tv.pluto.dynamic.cropping.android.framework.PlaybackState
import tv.pluto.dynamic.cropping.android.logic.CounterBasedCoordinatesProvider

class SimplePlayerIntegration(
    private val lifecycleOwner: LifecycleOwner,
    private val context: Context,
    private val mainDispatcher: CoroutineDispatcher,
    private val styledPlayerView: StyledPlayerView,
    private val video: Video,
    private val initialPlaybackPositionMs: Long,
    private val currentConsumedCoordinateIndex: Int,
    private val onPlaybackPositionChanged: (Long) -> Unit,
    private val onCoordinateIndexConsumed: (Int) -> Unit,
) : DefaultLifecycleObserver {

    private var exoPlayer: ExoPlayer? = null
    private var playbackState: PlaybackState = PlaybackState.PausedForegrounded

    init {
        lifecycleOwner.lifecycle.addObserver(this)
        createExoPlayer()
        setMediaItemAndPlay(createMediaItem())
    }

    fun destroy() {
        lifecycleOwner.lifecycle.removeObserver(this)
        exoPlayer?.release()
        exoPlayer = null
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
            styledPlayerView.player = exoPlayer

            val coordinatesProvider = CounterBasedCoordinatesProvider(
                video.coordinates,
                currentConsumedCoordinateIndex,
            )

            exoPlayer.setVideoFrameMetadataListener { _, _, _, _ ->
                lifecycleOwner.lifecycleScope.launch(mainDispatcher) {
                    onPlaybackPositionChanged(exoPlayer.currentPosition)
                    val (_, consumedIndex) = coordinatesProvider.getNextCoordinate()
                    onCoordinateIndexConsumed(consumedIndex)
                }
            }
        }
    }

    private fun setMediaItemAndPlay(mediaItem: MediaItem) {
        exoPlayer?.apply {
            setMediaItem(mediaItem)
            prepare()
            play()
            seekTo(initialPlaybackPositionMs)
        }
    }

    private fun createMediaItem(): MediaItem {
        val uri = RawResourceDataSource.buildRawResourceUri(video.videoResId)
        return MediaItem.fromUri(uri)
    }
}
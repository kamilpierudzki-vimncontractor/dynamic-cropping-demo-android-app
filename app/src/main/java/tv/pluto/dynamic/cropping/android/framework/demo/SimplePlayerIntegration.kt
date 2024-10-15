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
import tv.pluto.dynamic.cropping.android.framework.Metadata
import tv.pluto.dynamic.cropping.android.framework.PlaybackState

class SimplePlayerIntegration(
    private val lifecycleOwner: LifecycleOwner,
    private val context: Context,
    private val mainDispatcher: CoroutineDispatcher,
    private val styledPlayerView: StyledPlayerView,
    private val staticMetadata: Metadata,
    private val initialPlaybackPositionMs: Long,
    val onPlaybackPositionChanged: (Long) -> Unit,
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

            exoPlayer.setVideoFrameMetadataListener { _, _, _, _ ->
                lifecycleOwner.lifecycleScope.launch(mainDispatcher) {
                    onPlaybackPositionChanged(exoPlayer.currentPosition)
                }
            }
        }
    }

    private fun setMediaItemAndPlay(mediaItem: MediaItem) {
        exoPlayer?.apply {
            setMediaItem(mediaItem)
            prepare()
            play()
            android.util.Log.d("test-seeking", "SimplePlayerIntegration, seekTo: $initialPlaybackPositionMs")
            seekTo(initialPlaybackPositionMs)
        }
    }

    private fun createMediaItem(): MediaItem {
        val uri = RawResourceDataSource.buildRawResourceUri(staticMetadata.videoResId)
        return MediaItem.fromUri(uri)
    }
}
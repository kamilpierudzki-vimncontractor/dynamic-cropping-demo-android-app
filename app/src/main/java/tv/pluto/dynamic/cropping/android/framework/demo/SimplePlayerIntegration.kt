package tv.pluto.dynamic.cropping.android.framework.demo

import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import tv.pluto.dynamic.cropping.android.framework.Playback
import tv.pluto.dynamic.cropping.android.framework.Video

class SimplePlayerIntegration(
    private val lifecycleOwner: LifecycleOwner,
    private val context: Context,
    private val mainDispatcher: CoroutineDispatcher,
    private val styledPlayerView: StyledPlayerView,
    private val video: Video,
    private val initialPlaybackPositionMs: Long,
    private val onPlaybackPositionChanged: (Long) -> Unit,
    private val onVideoEnded: () -> Unit,
) : DefaultLifecycleObserver {

    private var playback: Playback? = null

    init {
        lifecycleOwner.lifecycle.addObserver(this)
        createExoPlayer()
    }

    fun destroy() {
        lifecycleOwner.lifecycle.removeObserver(this)
        playback?.destroy()
        playback = null
    }

    fun pause() {
        playback?.pause()
    }

    fun play(startFromPositionMs: Long) {
        playback?.play(startFromPositionMs)
    }

    override fun onStart(owner: LifecycleOwner) {
        playback?.onStart()
    }

    override fun onStop(owner: LifecycleOwner) {
        playback?.onStop()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        destroy()
    }

    private fun createExoPlayer() {
        ExoPlayer.Builder(context).build().also { exoPlayer ->
            this.playback = Playback(exoPlayer, video, initialPlaybackPositionMs)
            styledPlayerView.player = exoPlayer

            exoPlayer.setVideoFrameMetadataListener { _, _, _, _ ->
                lifecycleOwner.lifecycleScope.launch(mainDispatcher) {
                    onPlaybackPositionChanged(exoPlayer.currentPosition)
                }
            }

            exoPlayer.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    if (playbackState == Player.STATE_ENDED) {
                        lifecycleOwner.lifecycleScope.launch(mainDispatcher) {
                            exoPlayer.seekTo(0)
                            onVideoEnded()
                        }
                    }
                }
            })
        }
    }
}
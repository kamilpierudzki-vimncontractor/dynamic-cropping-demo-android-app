package tv.pluto.dynamic.cropping.android.framework

import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.upstream.RawResourceDataSource

private sealed interface PlaybackState {
    data object PausedForegrounded : PlaybackState
    data object PausedBackgrounded : PlaybackState
    data object PlayingForegrounded : PlaybackState
    data object PlayingBackgrounded : PlaybackState
}

class Playback(
    private val exoPlayer: ExoPlayer,
    private val video: Video,
    private val initialPlaybackPositionMs: Long,
) {

    private var playbackState: PlaybackState = PlaybackState.PausedForegrounded

    init {
        setMediaItem(createMediaItem())
    }

    fun destroy() {
        exoPlayer.release()
    }

    fun pause() {
        if (playbackState != PlaybackState.PausedForegrounded) {
            playbackState = PlaybackState.PausedForegrounded
            exoPlayer.pause()
        }
    }

    fun play(startFromPositionMs: Long) {
        if (playbackState != PlaybackState.PlayingForegrounded) {
            playbackState = PlaybackState.PlayingForegrounded
            exoPlayer.apply {
                seekTo(startFromPositionMs)
                play()
            }
        }
    }

    fun onStart() {
        when (playbackState) {
            PlaybackState.PlayingBackgrounded -> {
                exoPlayer.play()
                playbackState = PlaybackState.PlayingForegrounded
            }

            PlaybackState.PausedBackgrounded -> {
                playbackState = PlaybackState.PausedForegrounded
            }

            else -> {}
        }
    }

    fun onStop() {
        exoPlayer.let { player ->
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

    private fun setMediaItem(mediaItem: MediaItem) {
        exoPlayer.apply {
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
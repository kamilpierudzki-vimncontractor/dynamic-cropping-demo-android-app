package tv.pluto.dynamic.cropping.android.framework.demo

import androidx.lifecycle.DefaultLifecycleObserver
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView

interface ExoPlayerManagerV2 : DefaultLifecycleObserver {
    val videoName: String
    fun initializeAndStartPlayback(playerView: StyledPlayerView)
    fun pause()
    fun play()
    fun destroy()
}
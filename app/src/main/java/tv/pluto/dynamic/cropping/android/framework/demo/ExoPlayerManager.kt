package tv.pluto.dynamic.cropping.android.framework.demo

import androidx.lifecycle.DefaultLifecycleObserver
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView

interface ExoPlayerManager : DefaultLifecycleObserver {

    fun setPlayerView(playerView: StyledPlayerView, coordinates: DoubleArray)
    fun setMediaItemAndPlay(mediaItem: MediaItem)
    fun destroy()
}
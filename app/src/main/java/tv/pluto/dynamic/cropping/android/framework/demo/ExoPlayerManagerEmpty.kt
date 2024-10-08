package tv.pluto.dynamic.cropping.android.framework.demo

import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView

class ExoPlayerManagerEmpty : ExoPlayerManager {

    override fun setPlayerView(playerView: StyledPlayerView, coordinates: DoubleArray) {
    }

    override fun setMediaItemAndPlay(mediaItem: MediaItem) {
    }

    override fun destroy() {
    }
}
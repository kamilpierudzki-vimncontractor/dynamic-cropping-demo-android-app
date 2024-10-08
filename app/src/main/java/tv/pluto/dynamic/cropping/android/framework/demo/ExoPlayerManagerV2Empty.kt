package tv.pluto.dynamic.cropping.android.framework.demo

import com.google.android.exoplayer2.ui.StyledPlayerView

class ExoPlayerManagerV2Empty : ExoPlayerManagerV2 {

    override val videoName: String = "Ninja Turtles"

    override fun initializeAndStartPlayback(playerView: StyledPlayerView) {
    }

    override fun pause() {
    }

    override fun play() {
    }

    override fun destroy() {
    }
}
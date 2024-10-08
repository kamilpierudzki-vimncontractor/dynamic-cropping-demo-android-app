package tv.pluto.dynamic.cropping.android.framework.demo

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.video.VideoSize
import tv.pluto.dynamic.cropping.android.framework.PlayerWindowViewWrapperImpl
import tv.pluto.dynamic.cropping.android.logic.Height
import tv.pluto.dynamic.cropping.android.logic.InfiniteCoordinatesProvider
import tv.pluto.dynamic.cropping.android.logic.PlayerPositionCalculation
import tv.pluto.dynamic.cropping.android.logic.Size
import tv.pluto.dynamic.cropping.android.logic.Width

class ExoPlayerManagerImpl(
    private val activity: Activity,
) : ExoPlayerManager {

    private var exoPlayer: ExoPlayer? = null
    private var playerPositionCalculation: PlayerPositionCalculation? = null

    override fun setPlayerView(playerView: StyledPlayerView, coordinates: DoubleArray) {
        ExoPlayer.Builder(activity).build().also { exoPlayer ->
            this.exoPlayer = exoPlayer
            exoPlayer.repeatMode = Player.REPEAT_MODE_ALL
            playerView.player = exoPlayer
            playerView.useController = false

            playerPositionCalculation = PlayerPositionCalculation(
                PlayerWindowViewWrapperImpl(playerView),
                InfiniteCoordinatesProvider(coordinates),
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

    override fun setMediaItemAndPlay(mediaItem: MediaItem) {
        exoPlayer?.apply {
            setMediaItem(mediaItem)
            prepare()
            play()
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        exoPlayer?.pause()
    }

    override fun onStart(owner: LifecycleOwner) {
        exoPlayer?.also {
            if (!it.isPlaying) {
                it.play()
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
}
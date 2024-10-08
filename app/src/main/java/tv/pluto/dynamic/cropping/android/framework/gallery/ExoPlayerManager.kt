package tv.pluto.dynamic.cropping.android.framework.gallery

import android.app.Activity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.video.VideoSize
import tv.pluto.dynamic.cropping.android.framework.PlayerWindowViewFactory
import tv.pluto.dynamic.cropping.android.framework.Type
import tv.pluto.dynamic.cropping.android.logic.Height
import tv.pluto.dynamic.cropping.android.logic.InfiniteCoordinatesProvider
import tv.pluto.dynamic.cropping.android.logic.PlayerPositionCalculation
import tv.pluto.dynamic.cropping.android.logic.Size
import tv.pluto.dynamic.cropping.android.logic.Width

class ExoPlayerManager(
    private val activity: Activity,
) : DefaultLifecycleObserver {

    private val playerWindowViewFactory = PlayerWindowViewFactory()
    private var exoPlayer: ExoPlayer? = null
    private var playerPositionCalculation: PlayerPositionCalculation? = null

    fun setPlayerView(playerView: StyledPlayerView, coordinates: DoubleArray) {
        ExoPlayer.Builder(activity).build().also { exoPlayer ->
            this.exoPlayer = exoPlayer
            exoPlayer.repeatMode = Player.REPEAT_MODE_ALL
            playerView.player = exoPlayer
            playerView.useController = false

            playerPositionCalculation = PlayerPositionCalculation(
                playerWindowViewFactory.create(playerView, Type.TranslationXWithSkipping),
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

    fun setMediaItemAndPlay(mediaItem: MediaItem) {
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

    fun destroy() {
        exoPlayer?.release()
        exoPlayer = null
        playerPositionCalculation = null
    }
}
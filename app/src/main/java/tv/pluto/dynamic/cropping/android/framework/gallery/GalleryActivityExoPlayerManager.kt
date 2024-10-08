package tv.pluto.dynamic.cropping.android.framework.gallery

import android.app.Activity
import androidx.lifecycle.DefaultLifecycleObserver
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

sealed interface PlayerOnScreen {
    data object Full : PlayerOnScreen
    data object Center : PlayerOnScreen
    data class Dynamic(val coordinates: DoubleArray) : PlayerOnScreen {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Dynamic

            return coordinates.contentEquals(other.coordinates)
        }

        override fun hashCode(): Int {
            return coordinates.contentHashCode()
        }
    }
}

class GalleryActivityExoPlayerManager(
    private val activity: Activity,
) : DefaultLifecycleObserver {

    private var fullExoPlayer: ExoPlayer? = null
    private var centeredExoPlayer: ExoPlayer? = null
    private var dynamicExoPlayer: ExoPlayer? = null
    private var playerPositionCalculation: PlayerPositionCalculation? = null

    fun setPlayerView(playerOnScreen: PlayerOnScreen, playerWindowView: StyledPlayerView) {
        when (playerOnScreen) {
            PlayerOnScreen.Full -> {
                fullExoPlayer = ExoPlayer.Builder(activity).build()
                fullExoPlayer
            }

            PlayerOnScreen.Center -> {
                centeredExoPlayer = ExoPlayer.Builder(activity).build()
                centeredExoPlayer
            }

            is PlayerOnScreen.Dynamic -> {
                dynamicExoPlayer = ExoPlayer.Builder(activity).build()
                dynamicExoPlayer
            }
        }?.also { exoPlayer ->
            exoPlayer.repeatMode = Player.REPEAT_MODE_ALL
            playerWindowView.player = exoPlayer
            playerWindowView.useController = false

            if (playerOnScreen is PlayerOnScreen.Dynamic) {
                setupDynamicCropping(exoPlayer, playerWindowView, playerOnScreen.coordinates)
            }
        }
    }

    fun setMediaItemAndPlay(playerOnScreen: PlayerOnScreen, mediaItem: MediaItem) {
        when (playerOnScreen) {
            PlayerOnScreen.Full -> fullExoPlayer
            PlayerOnScreen.Center -> centeredExoPlayer
            is PlayerOnScreen.Dynamic -> dynamicExoPlayer
        }?.apply {
            setMediaItem(mediaItem)
            prepare()
            play()
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        fullExoPlayer?.pause()
        centeredExoPlayer?.pause()
        dynamicExoPlayer?.pause()
    }

    override fun onStart(owner: LifecycleOwner) {
        fullExoPlayer?.also {
            if (!it.isPlaying) {
                it.play()
            }
        }
        centeredExoPlayer?.also {
            if (!it.isPlaying) {
                it.play()
            }
        }
        dynamicExoPlayer?.also {
            if (!it.isPlaying) {
                it.play()
            }
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        destroy()
    }

    fun destroy() {
        fullExoPlayer?.release()
        fullExoPlayer = null

        centeredExoPlayer?.release()
        centeredExoPlayer = null

        dynamicExoPlayer?.release()
        dynamicExoPlayer = null
    }

    private fun setupDynamicCropping(
        exoPlayer: ExoPlayer,
        playerWindowView: StyledPlayerView,
        coordinates: DoubleArray,
    ) {
        playerPositionCalculation = PlayerPositionCalculation(
            PlayerWindowViewWrapperImpl(playerWindowView),
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
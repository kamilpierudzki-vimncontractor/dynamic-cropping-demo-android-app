package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import tv.pluto.dynamic.cropping.android.framework.FixedAspectStyledPlayerView
import tv.pluto.dynamic.cropping.android.framework.demo.ExoPlayerManager

@Composable
fun DemoVideoComponent(
    exoPlayerManager: ExoPlayerManager,
    modifier: Modifier = Modifier,
) {
    AndroidView(
        modifier = modifier.clipToBounds(),
        factory = { context ->
            FixedAspectStyledPlayerView(context)
                .also { playerView ->
                    playerView.setAspectRatio(10, 16)
                    playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT
                    exoPlayerManager.initializeAndStartPlayback(playerView)
                }
        },
    )
}
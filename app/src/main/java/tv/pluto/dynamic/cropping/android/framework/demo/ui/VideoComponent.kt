package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import tv.pluto.dynamic.cropping.android.framework.FixedAspectStyledPlayerView
import tv.pluto.dynamic.cropping.android.framework.demo.ExoPlayerManagerV2

@Composable
fun VideoComponent(
    exoPlayerManagerV2: ExoPlayerManagerV2,
    modifier: Modifier = Modifier,
) {
    AndroidView(
        modifier = modifier.clipToBounds(),
        factory = { context ->
            FixedAspectStyledPlayerView(context)
                .also { playerView ->
                    playerView.setAspectRatio(9, 16)
                    playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT
                    exoPlayerManagerV2.initializeAndStartPlayback(playerView)
                }
        },
    )
}
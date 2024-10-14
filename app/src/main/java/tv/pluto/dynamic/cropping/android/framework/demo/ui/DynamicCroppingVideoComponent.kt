package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.viewinterop.AndroidView
import tv.pluto.dynamic.cropping.android.framework.FixedAspectTextureView
import tv.pluto.dynamic.cropping.android.framework.demo.DynamicCroppingPlayerIntegration

@Composable
fun DynamicCroppingVideoComponent(
    dynamicCroppingPlayerIntegration: DynamicCroppingPlayerIntegration,
    modifier: Modifier = Modifier,
) {
    AndroidView(
        modifier = modifier.clipToBounds(),
        factory = { context ->
            FixedAspectTextureView(context)
                .apply {
                    setAspectRatio(10, 16)
                }
                .also { playerView ->
                    dynamicCroppingPlayerIntegration.initializeAndStartPlayback(playerView)
                }
        },
    )
}
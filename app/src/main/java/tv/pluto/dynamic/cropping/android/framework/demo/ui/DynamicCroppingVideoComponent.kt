package tv.pluto.dynamic.cropping.android.framework.demo.ui

import android.view.TextureView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    var lastTextureView by remember { mutableStateOf<TextureView?>(null) }

    AndroidView(
        modifier = modifier.clipToBounds(),
        factory = { context ->
            android.util.Log.d("test123", "factory {}, ${dynamicCroppingPlayerIntegration.video.title}")
            FixedAspectTextureView(context)
                .apply {
                    setAspectRatio(10, 16)
                }
                .also { textureView ->
                    lastTextureView = textureView
                }
        },
        onRelease = {
            android.util.Log.d("test123", "onRelease {}, ${dynamicCroppingPlayerIntegration.video.title}")
            dynamicCroppingPlayerIntegration.onUiReleased()
        },
        update = {
            android.util.Log.d("test123", "update {}, ${dynamicCroppingPlayerIntegration.video.title}")
            lastTextureView?.let {
                dynamicCroppingPlayerIntegration.onUiCreated(it)
            }
        }
    )
}
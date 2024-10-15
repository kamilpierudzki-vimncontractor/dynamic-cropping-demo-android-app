package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.Dispatchers
import tv.pluto.dynamic.cropping.android.framework.FixedAspectTextureView
import tv.pluto.dynamic.cropping.android.framework.Metadata
import tv.pluto.dynamic.cropping.android.framework.demo.DynamicCroppingPlayerIntegration

@Composable
fun DynamicCroppingVideoComponent(
    lifecycleOwner: LifecycleOwner,
    staticMetadata: Metadata,
    playbackState: Boolean,
    initialPlaybackPositionMs: Long,
    onPlaybackPositionChanged: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    var dynamicCroppingPlayerIntegration by remember { mutableStateOf<DynamicCroppingPlayerIntegration?>(null) }

    AndroidView(
        factory = { context ->
            android.util.Log.d("test123", "factory {}, ${staticMetadata.title}")
            FixedAspectTextureView(context)
                .apply {
                    setAspectRatio(9, 16)
                }
                .also { textureView ->
                    dynamicCroppingPlayerIntegration = DynamicCroppingPlayerIntegration(
                        lifecycleOwner = lifecycleOwner,
                        context = context,
                        mainDispatcher = Dispatchers.Main,
                        textureView = textureView,
                        staticMetadata = staticMetadata,
                        initialPlaybackPositionMs = initialPlaybackPositionMs,
                        onPlaybackPositionChanged = onPlaybackPositionChanged,
                    )
                }
        },
        modifier = modifier.clipToBounds(),
        onRelease = {
            android.util.Log.d("test123", "onRelease {}, ${staticMetadata.title}")
            dynamicCroppingPlayerIntegration?.destroy()
        },
        update = {
            android.util.Log.d("test123", "update {}, ${staticMetadata.title}, play: ${playbackState}")
            if (playbackState) {
                dynamicCroppingPlayerIntegration?.play()
            } else {
                dynamicCroppingPlayerIntegration?.pause()
            }
        }
    )
}
package tv.pluto.dynamic.cropping.android.framework.ui

import android.view.TextureView
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
import tv.pluto.dynamic.cropping.android.framework.DynamicCroppingPlayerIntegration
import tv.pluto.dynamic.cropping.android.framework.Metadata

@Composable
fun DynamicCroppingVideoComponent(
    lifecycleOwner: LifecycleOwner,
    staticMetadata: Metadata,
    playbackState: Boolean,
    playbackPositionMs: Long,
    coordinateIndex: Int,
    onPlaybackPositionChanged: (Long) -> Unit,
    onCoordinateIndexConsumed: (Int) -> Unit,
    onVideoEnded: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var dynamicCroppingPlayerIntegration by remember { mutableStateOf<DynamicCroppingPlayerIntegration?>(null) }

    AndroidView(
        factory = { context ->
            TextureView(context)
                .also { textureView ->
                    dynamicCroppingPlayerIntegration = DynamicCroppingPlayerIntegration(
                        lifecycleOwner = lifecycleOwner,
                        context = context,
                        mainDispatcher = Dispatchers.Main,
                        textureView = textureView,
                        staticMetadata = staticMetadata,
                        initialPlaybackPositionMs = playbackPositionMs,
                        initialCoordinateIndex = coordinateIndex,
                        onPlaybackPositionChanged = onPlaybackPositionChanged,
                        onCoordinateIndexConsumed = onCoordinateIndexConsumed,
                        onVideoEnded = onVideoEnded,
                    )
                }
        },
        modifier = modifier.clipToBounds(),
        onRelease = {
            dynamicCroppingPlayerIntegration?.destroy()
            dynamicCroppingPlayerIntegration = null
        },
        update = {
            if (playbackState) {
                dynamicCroppingPlayerIntegration?.play(playbackPositionMs)
            } else {
                dynamicCroppingPlayerIntegration?.pause()
            }
        }
    )
}
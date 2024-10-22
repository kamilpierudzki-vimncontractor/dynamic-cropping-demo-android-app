package tv.pluto.dynamic.cropping.android.framework.ui

import android.view.TextureView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import tv.pluto.dynamic.cropping.android.framework.Video

@Composable
fun DynamicCroppingVideoComponent(
    lifecycleOwner: LifecycleOwner,
    video: Video,
    playbackState: Boolean,
    getPlaybackPositionMs: () -> Long,
    onPlaybackPositionChanged: (Long) -> Unit,
    onVideoEnded: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var dynamicCroppingPlayerIntegration by remember { mutableStateOf<DynamicCroppingPlayerIntegration?>(null) }

    LaunchedEffect(playbackState) {
        if (playbackState) {
            dynamicCroppingPlayerIntegration?.play(getPlaybackPositionMs())
        } else {
            dynamicCroppingPlayerIntegration?.pause()
        }
    }

    AndroidView(
        factory = { context ->
            TextureView(context)
                .also { textureView ->
                    dynamicCroppingPlayerIntegration = DynamicCroppingPlayerIntegration(
                        lifecycleOwner = lifecycleOwner,
                        context = context,
                        mainDispatcher = Dispatchers.Main,
                        textureView = textureView,
                        video = video,
                        initialPlaybackPositionMs = getPlaybackPositionMs(),
                        onPlaybackPositionChanged = onPlaybackPositionChanged,
                        onVideoEnded = onVideoEnded,
                    )
                }
        },
        modifier = modifier.clipToBounds(),
        onRelease = {
            dynamicCroppingPlayerIntegration?.destroy()
            dynamicCroppingPlayerIntegration = null
        },
    )
}
package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.google.android.exoplayer2.ui.StyledPlayerView
import kotlinx.coroutines.Dispatchers
import tv.pluto.dynamic.cropping.android.framework.Video
import tv.pluto.dynamic.cropping.android.framework.demo.SimplePlayerIntegration

@Composable
fun SimpleVideoComponent(
    lifecycleOwner: LifecycleOwner,
    video: Video,
    initialPlaybackPositionMs: Long,
    onPlaybackPositionChanged: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var playerIntegration by remember { mutableStateOf<SimplePlayerIntegration?>(null) }

    AndroidView(
        factory = { context ->
            StyledPlayerView(context)
                .apply {
                    useController = false
                }
                .also { styledPlayerView ->
                    playerIntegration = SimplePlayerIntegration(
                        lifecycleOwner = lifecycleOwner,
                        context = context,
                        mainDispatcher = Dispatchers.Main,
                        styledPlayerView = styledPlayerView,
                        video = video,
                        initialPlaybackPositionMs = initialPlaybackPositionMs,
                        onPlaybackPositionChanged = onPlaybackPositionChanged,
                    )
                }

        },
        modifier = modifier.background(Color.Black),
        onRelease = {
            playerIntegration?.destroy()
            playerIntegration = null
        },
    )
}
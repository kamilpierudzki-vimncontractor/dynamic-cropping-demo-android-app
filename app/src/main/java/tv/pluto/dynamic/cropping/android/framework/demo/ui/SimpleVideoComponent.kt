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
    playbackState: Boolean,
    playbackPositionMs: Long,
    onPlaybackPositionChanged: (Long) -> Unit,
    onVideoEnded: () -> Unit,
    modifier: Modifier = Modifier
) {
    var simplePlayerIntegration by remember { mutableStateOf<SimplePlayerIntegration?>(null) }

    AndroidView(
        factory = { context ->
            StyledPlayerView(context)
                .apply {
                    useController = false
                }
                .also { styledPlayerView ->
                    simplePlayerIntegration = SimplePlayerIntegration(
                        lifecycleOwner = lifecycleOwner,
                        context = context,
                        mainDispatcher = Dispatchers.Main,
                        styledPlayerView = styledPlayerView,
                        video = video,
                        initialPlaybackPositionMs = playbackPositionMs,
                        onPlaybackPositionChanged = onPlaybackPositionChanged,
                        onVideoEnded = onVideoEnded,
                    )
                }

        },
        modifier = modifier.background(Color.Black),
        onRelease = {
            simplePlayerIntegration?.destroy()
            simplePlayerIntegration = null
        },
        update = {
            if (playbackState) {
                simplePlayerIntegration?.play(playbackPositionMs)
            } else {
                simplePlayerIntegration?.pause()
            }
        }
    )
}
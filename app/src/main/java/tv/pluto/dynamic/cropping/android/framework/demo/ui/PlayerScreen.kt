package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.android.exoplayer2.ui.StyledPlayerView
import kotlinx.coroutines.Dispatchers
import tv.pluto.dynamic.cropping.android.framework.Metadata
import tv.pluto.dynamic.cropping.android.framework.VideoPlaybackViewModel
import tv.pluto.dynamic.cropping.android.framework.demo.SimplePlayerIntegration

@Composable
fun PlayerScreen(videoPlaybackViewModel: VideoPlaybackViewModel) {
    val currentMetadata by videoPlaybackViewModel.currentMetadata
    val currentPlaybackPosition by videoPlaybackViewModel.currentPlaybackPositionState

    Box(modifier = Modifier
        .fillMaxSize()) {
        SimpleVideoComponent(
            lifecycleOwner = LocalLifecycleOwner.current,
            staticMetadata = currentMetadata,
            initialPlaybackPositionMs = currentPlaybackPosition,
            onPlaybackPositionChanged = { newPositionMs ->
                videoPlaybackViewModel.onVideoPositionChanged(
                    videoPlaybackViewModel.currentIndexOfPlayingVideo.value,
                    newPositionMs,
                )
            },
            modifier = Modifier
                .matchParentSize()
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun SimpleVideoComponent(
    lifecycleOwner: LifecycleOwner,
    staticMetadata: Metadata,
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
                        staticMetadata = staticMetadata,
                        initialPlaybackPositionMs = initialPlaybackPositionMs,
                        onPlaybackPositionChanged = onPlaybackPositionChanged,
                    )
                }

        },
        modifier = modifier.background(Color.Black),
        onRelease = {
            playerIntegration?.destroy()
        },
    )
}
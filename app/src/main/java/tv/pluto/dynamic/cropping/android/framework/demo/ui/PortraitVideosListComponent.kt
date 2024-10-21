package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tv.pluto.dynamic.cropping.android.framework.Video

@Composable
fun PortraitVideosListComponent(
    videos: List<Video>,
    videoPlayingStates: Map<Int, Boolean>,
    videoPositionStates: Map<Int, Long>,
    lazyListState: LazyListState,
    flingBehavior: FlingBehavior,
    onVideoEnded: () -> Unit,
    onVideoPositionChanged: (Int, Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        flingBehavior = flingBehavior,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(top = 75.dp, bottom = 64.dp),
    ) {
        items(videos.size) { globalIndex ->
            val videoPlaying = videoPlayingStates[globalIndex] ?: false
            val playbackPositionMs = videoPositionStates[globalIndex] ?: 0

            PortraitCardComponent(
                video = videos[globalIndex],
                playbackState = videoPlaying,
                playbackPositionMs = playbackPositionMs,
                onPlaybackPositionChanged = { newPositionMs ->
                    onVideoPositionChanged(globalIndex, newPositionMs)
                },
                onVideoEnded = onVideoEnded,
            )
        }
    }
}
package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import tv.pluto.dynamic.cropping.android.framework.LandscapeCardComponent
import tv.pluto.dynamic.cropping.android.framework.Video

@Composable
fun LandscapeVideoListComponent(
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
    ) {
        items(videos.size) { globalIndex ->
            val videoPlaying = videoPlayingStates[globalIndex] ?: false
            val playbackPositionMs = videoPositionStates[globalIndex] ?: 0

            LandscapeCardComponent(
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
package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
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
fun VideosListComponent(
    videos: List<Video>,
    videoPlayingStates: Map<Int, Boolean>,
    videoPositionStates: Map<Int, Long>,
    coordinateIndicesStates: Map<Int, Int>,
    lazyListState: LazyListState,
    onVideoPositionChanged: (Int, Long) -> Unit,
    onCoordinateIndexConsumed: (Int, Int) -> Unit,
    onVideoEnded: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val snapBehavior = rememberSnapFlingBehavior(lazyListState)

    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        flingBehavior = snapBehavior,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(top = 75.dp, bottom = 64.dp),
        userScrollEnabled = false,
    ) {
        items(videos.size) { globalIndex ->
            val videoPlaying = videoPlayingStates[globalIndex] ?: false
            val playbackPositionMs = videoPositionStates[globalIndex] ?: 0
            val coordinateIndex = coordinateIndicesStates[globalIndex] ?: 0

            CardComponent(
                video = videos[globalIndex],
                playbackState = videoPlaying,
                playbackPositionMs = playbackPositionMs,
                coordinateIndex = coordinateIndex,
                onPlaybackPositionChanged = { newPositionMs ->
                    onVideoPositionChanged(globalIndex, newPositionMs)
                },
                onCoordinateIndexConsumed = { consumedIndexOfCoordinate ->
                    onCoordinateIndexConsumed(globalIndex, consumedIndexOfCoordinate)
                },
                onVideoEnded = {
                    onVideoEnded(globalIndex)
                },
            )
        }
    }
}
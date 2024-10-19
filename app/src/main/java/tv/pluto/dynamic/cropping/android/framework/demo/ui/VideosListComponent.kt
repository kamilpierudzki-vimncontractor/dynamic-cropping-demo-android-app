package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
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
    onSelectedItemIndexChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val snapBehavior = rememberSnapFlingBehavior(lazyListState)
    val coroutineScope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        flingBehavior = snapBehavior,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(top = 75.dp, bottom = 64.dp),
    ) {
        items(videos.size) { globalIndex ->
            LaunchedEffect(lazyListState) {
                snapshotFlow { lazyListState.layoutInfo }
                    .collect { layoutInfo ->
                        val fullyVisibleItems = layoutInfo.visibleItemsInfo
                            .filter { itemInfo ->
                                val itemStart = itemInfo.offset
                                val itemEnd = itemInfo.offset + itemInfo.size
                                val viewportStart = lazyListState.layoutInfo.viewportStartOffset
                                val viewportEnd = lazyListState.layoutInfo.viewportEndOffset

                                itemStart >= viewportStart && itemEnd <= viewportEnd
                            }

                        val firstFullyVisibleItemInfoIndex = fullyVisibleItems.firstOrNull()?.index ?: return@collect
                        selectedItemIndex = firstFullyVisibleItemInfoIndex
                        onSelectedItemIndexChanged(firstFullyVisibleItemInfoIndex)
                        coroutineScope.launch {
                            lazyListState.animateScrollToItem(firstFullyVisibleItemInfoIndex)
                        }
                    }
            }

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
                    val nextIndex = if (selectedItemIndex + 1 < videos.size) {
                        selectedItemIndex + 1
                    } else {
                        0
                    }
                    coroutineScope.launch {
                        lazyListState.animateScrollToItem(nextIndex)
                    }
                },
            )
        }
    }
}
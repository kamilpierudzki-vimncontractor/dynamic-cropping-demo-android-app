package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.launch
import tv.pluto.dynamic.cropping.android.framework.ListPositionCalculation
import tv.pluto.dynamic.cropping.android.framework.VideoPlaybackViewModel

private val listPositionCalculation = ListPositionCalculation()

@Composable
fun DemoApp(
    videoPlaybackViewModel: VideoPlaybackViewModel,
    isPortrait: Boolean,
) {
    val lazyListState = rememberLazyListState()
    val snapBehavior = rememberSnapFlingBehavior(lazyListState)
    val coroutineScope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    LaunchedEffect(selectedItemIndex) {
        videoPlaybackViewModel.onIndexOfPlayingComponentChanged(selectedItemIndex)
    }

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
                selectedItemIndex = listPositionCalculation.calculatedPosition(
                    requestedIndex = firstFullyVisibleItemInfoIndex,
                    currentIndex = selectedItemIndex,
                )

                coroutineScope.launch {
                    lazyListState.animateScrollToItem(selectedItemIndex)
                }
            }
    }

    val onVideoEnded: () -> Unit = {
        val nextIndex = if (selectedItemIndex + 1 < videoPlaybackViewModel.videos.size) {
            selectedItemIndex + 1
        } else {
            0
        }
        coroutineScope.launch {
            lazyListState.animateScrollToItem(nextIndex)
        }
    }

    val onVideoPositionChanged: (Int, Long) -> Unit = { indexOfPlayingComponent, newPosition ->
        videoPlaybackViewModel.onVideoPositionChanged(indexOfPlayingComponent, newPosition)
    }

    if (isPortrait) {
        PortraitHomeScreen(
            videoPlaybackViewModel = videoPlaybackViewModel,
            lazyListState = lazyListState,
            flingBehavior = snapBehavior,
            onVideoEnded = onVideoEnded,
            onVideoPositionChanged = onVideoPositionChanged,
        )
    } else {
        LandscapeHomeScreen(
            videoPlaybackViewModel = videoPlaybackViewModel,
            lazyListState = lazyListState,
            flingBehavior = snapBehavior,
            onVideoEnded = onVideoEnded,
            onVideoPositionChanged = onVideoPositionChanged,
        )
    }
}
package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch
import tv.pluto.dynamic.cropping.android.framework.VideoPlaybackViewModel

@Composable
fun HomeScreen(videoPlaybackViewModel: VideoPlaybackViewModel) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(color = Color.Black),
            ) {

                var selectedItemIndex by remember { mutableIntStateOf(0) }
                val lazyListState = rememberLazyListState()
                val coroutineScope = rememberCoroutineScope()
                val videoPlayingStates by videoPlaybackViewModel.videoPlayingStates
                val videoPositionStates by videoPlaybackViewModel.videoPositionStates
                val coordinateIndicesStates by videoPlaybackViewModel.consumedCoordinateIndicesStates

                LaunchedEffect(selectedItemIndex) {
                    videoPlaybackViewModel.onIndexOfPlayingComponentChanged(selectedItemIndex)
                    coroutineScope.launch {
                        lazyListState.animateScrollToItem(selectedItemIndex)
                    }
                }

                VideosListComponent(
                    videos = videoPlaybackViewModel.videos,
                    videoPlayingStates = videoPlayingStates,
                    videoPositionStates = videoPositionStates,
                    coordinateIndicesStates = coordinateIndicesStates,
                    lazyListState = lazyListState,
                    onVideoPositionChanged = { indexOfPlayingComponent, newPosition ->
                        videoPlaybackViewModel.onVideoPositionChanged(indexOfPlayingComponent, newPosition)
                    },
                    onCoordinateIndexConsumed = { indexOfPlayingComponent, consumedIndexOfCoordinate ->
                        videoPlaybackViewModel.onCoordinateIndexConsumed(
                            indexOfPlayingComponent,
                            consumedIndexOfCoordinate,
                        )
                    },
                    onVideoEnded = { videoPlaybackViewModel.onVideoEnded(it) },
                    modifier = Modifier.matchParentSize()
                )

                SwipeableBox(
                    onSwipe = { direction ->
                        coroutineScope.launch {
                            val nextIndex = when (direction) {
                                SwipeDirection.Down -> {
                                    if (selectedItemIndex + 1 < videoPlaybackViewModel.videos.size) {
                                        selectedItemIndex + 1
                                    } else {
                                        return@launch
                                    }
                                }

                                SwipeDirection.Up -> {
                                    if (selectedItemIndex - 1 >= 0) {
                                        selectedItemIndex - 1
                                    } else {
                                        return@launch
                                    }
                                }
                            }
                            selectedItemIndex = nextIndex
                        }
                    },
                    modifier = Modifier.matchParentSize()
                )

                TopSelectionComponent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopStart),
                )

                BottomBarComponent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart),
                )
            }
        },
    )
}
package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import tv.pluto.dynamic.cropping.android.framework.VideoPlaybackViewModel

private val backgroundGradientColors = listOf(
    Color.Black,
    Color.DarkGray,
    Color.Gray,
    Color.DarkGray,
    Color.Black,
)

private val alpha = 0.7f
private val gradientOnVideos = listOf(
    Color.Black.copy(alpha = alpha),
    Color.DarkGray.copy(alpha = alpha),
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.DarkGray.copy(alpha = alpha),
    Color.Black.copy(alpha = alpha),
)

@Composable
fun HomeScreen(videoPlaybackViewModel: VideoPlaybackViewModel) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(brush = Brush.verticalGradient(backgroundGradientColors)),
            ) {
                val lazyListState = rememberLazyListState()
                val snapBehavior = rememberSnapFlingBehavior(lazyListState)
                var lastSelectedIndex by remember { mutableIntStateOf(-1) }
                val videoPlayingStates by videoPlaybackViewModel.videoPlayingStates

                LazyColumn(
                    modifier = Modifier.matchParentSize(),
                    state = lazyListState,
                    flingBehavior = snapBehavior,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    contentPadding = PaddingValues(top = 64.dp, bottom = 64.dp),
                ) {

                    items(videoPlaybackViewModel.metadatas.size) { globalIndex ->
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

                                    val firstFullyVisibleItemInfoIndex = fullyVisibleItems.firstOrNull()?.index ?: 0
                                    if (lastSelectedIndex != firstFullyVisibleItemInfoIndex) {
                                        lastSelectedIndex = firstFullyVisibleItemInfoIndex
                                        videoPlaybackViewModel.updateCurrentlyPlayingVideo(lastSelectedIndex)
                                    }
                                }
                        }

                        val videoPlayingState = videoPlayingStates[globalIndex] ?: false

                        CardComponent(
                            staticMetadata = videoPlaybackViewModel.metadatas[globalIndex],
                            playbackState = videoPlayingState,
                            onPlaybackPositionChanged = { newPositionMs ->
                                videoPlaybackViewModel.updateVideoPosition(globalIndex, newPositionMs)
                            }
                        )
                    }
                }
                Spacer(
                    modifier = Modifier
                        .matchParentSize()
                        .background(brush = Brush.verticalGradient(gradientOnVideos))
                )
                TopSelectionComponent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
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
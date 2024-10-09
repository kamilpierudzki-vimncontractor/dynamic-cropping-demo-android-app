package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import tv.pluto.dynamic.cropping.android.framework.demo.ExoPlayerManager

private val gradientColors = listOf(
    Color.Black,
    Color.DarkGray,
    Color.Gray,
    Color.DarkGray,
    Color.Black
)

@Composable
fun HomeScreen(
    exoPlayerManagers: List<ExoPlayerManager>
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(brush = Brush.verticalGradient(gradientColors)),
            ) {
                val lazyListState = rememberLazyListState()
                val snapBehavior = rememberSnapFlingBehavior(lazyListState)

                LazyColumn(
                    modifier = Modifier.matchParentSize(),
                    state = lazyListState,
                    flingBehavior = snapBehavior,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    contentPadding = PaddingValues(top = 64.dp, bottom = 64.dp),
                ) {
                    items(exoPlayerManagers.size) { index ->
                        LaunchedEffect(key1 = index) {
                            exoPlayerManagers[index].play()
                        }
                        DisposableEffect(key1 = index) {
                            onDispose {
                                exoPlayerManagers[index].pause()
                            }
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

                                    fullyVisibleItems.firstOrNull()?.let { focusedItemInfo ->
                                        exoPlayerManagers[index].play()

                                        exoPlayerManagers
                                            .filterIndexed { managerIndex, _ -> managerIndex != focusedItemInfo.index }
                                            .forEach { manager -> manager.pause() }
                                    }
                                }
                        }

                        CardComponent(
                            exoPlayerManager = exoPlayerManagers[index],
                        )
                    }
                }
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
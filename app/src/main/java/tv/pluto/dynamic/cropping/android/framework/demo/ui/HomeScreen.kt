package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import tv.pluto.dynamic.cropping.android.framework.VideoPlaybackViewModel

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
                    .background(color = Color.Black),
            ) {
                val videoPlayingStates by videoPlaybackViewModel.videoPlayingStates
                val videoPositionStates by videoPlaybackViewModel.videoPositionStates

                VideosListComponent(
                    videos = videoPlaybackViewModel.videos,
                    videoPlayingStates = videoPlayingStates,
                    videoPositionStates = videoPositionStates,
                    onVideoPositionChanged = { indexOfPlayingComponent, newPosition ->
                        videoPlaybackViewModel.onVideoPositionChanged(indexOfPlayingComponent, newPosition)
                    },
                    onSelectedItemIndexChanged = { newSelectedItemIndex ->
                        videoPlaybackViewModel.onIndexOfPlayingComponentChanged(newSelectedItemIndex)
                    },
                    modifier = Modifier.matchParentSize()
                )
                Spacer(
                    modifier = Modifier
                        .matchParentSize()
                        .background(brush = Brush.verticalGradient(gradientOnVideos))
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
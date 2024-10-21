package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import tv.pluto.dynamic.cropping.android.framework.VideoPlaybackViewModel

private val alpha = 0.6f
private val gradientOnVideosTop = listOf(
    Color.Gray.copy(alpha = alpha),
    Color.Transparent,
)
private val gradientOnVideosBottom = listOf(
    Color.Transparent,
    Color.Gray.copy(alpha = alpha),
)

@Composable
fun HomeScreen(videoPlaybackViewModel: VideoPlaybackViewModel) {
    val configuration = LocalConfiguration.current
    val videoCardHeightDp = configuration.screenHeightDp.dp * 0.7f
    val listTopPadding = 90.dp
    val bottomGradientHeightDp = configuration.screenHeightDp.dp - videoCardHeightDp - listTopPadding

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
                    videoCardHeightDp = videoCardHeightDp,
                    listTopPadding = listTopPadding,
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
                        .fillMaxWidth()
                        .height(listTopPadding)
                        .background(brush = Brush.verticalGradient(gradientOnVideosTop))
                        .align(Alignment.TopCenter)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(bottomGradientHeightDp)
                        .background(brush = Brush.verticalGradient(gradientOnVideosBottom))
                        .align(Alignment.BottomCenter)
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
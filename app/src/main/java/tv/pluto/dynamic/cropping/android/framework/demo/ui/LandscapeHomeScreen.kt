package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import tv.pluto.dynamic.cropping.android.framework.VideoPlaybackViewModel
import tv.pluto.dynamic.cropping.android.framework.theme.typography.plutoTVSans10

@Composable
fun LandscapeHomeScreen(
    videoPlaybackViewModel: VideoPlaybackViewModel,
    lazyListState: LazyListState,
    flingBehavior: FlingBehavior,
    onVideoEnded: () -> Unit,
    onVideoPositionChanged: (Int, Long) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .background(color = Color.Black)
            .fillMaxSize(),
    ) {
        val videoPlayingStates by videoPlaybackViewModel.videoPlayingStates
        val videoPositionStates by videoPlaybackViewModel.videoPositionStates

        val (listComponent, categoryLabel) = createRefs()

        LandscapeVideoListComponent(
            videos = videoPlaybackViewModel.videos,
            videoPlayingStates = videoPlayingStates,
            videoPositionStates = videoPositionStates,
            lazyListState = lazyListState,
            flingBehavior = flingBehavior,
            onVideoEnded = onVideoEnded,
            onVideoPositionChanged = onVideoPositionChanged,
            modifier = Modifier.constrainAs(listComponent) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = "You May Also Like",
            modifier = Modifier
                .constrainAs(categoryLabel) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .padding(start = 40.dp, top = 40.dp),
            fontFamily = plutoTVSans10,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = Color(0xE6FFFFFF),
            style = TextStyle(
                lineHeight = 16.sp,
            )
        )
    }
}
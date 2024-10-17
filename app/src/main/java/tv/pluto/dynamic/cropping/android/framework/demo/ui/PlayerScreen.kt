package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.LocalLifecycleOwner
import tv.pluto.dynamic.cropping.android.framework.VideoPlaybackViewModel
import tv.pluto.dynamic.cropping.android.framework.theme.typography.plutoTVSans10

@Composable
fun PlayerScreen(videoPlaybackViewModel: VideoPlaybackViewModel) {
    val currentVideo by videoPlaybackViewModel.currentVideo
    val currentPlaybackPosition by videoPlaybackViewModel.currentPlaybackPositionState

    ConstraintLayout(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize(),
    ) {
        val (playerComponent, ctaComponent, metadataComponent, categoryLabel) = createRefs()

        SimpleVideoComponent(
            lifecycleOwner = LocalLifecycleOwner.current,
            video = currentVideo,
            initialPlaybackPositionMs = currentPlaybackPosition,
            onPlaybackPositionChanged = { newPositionMs ->
                videoPlaybackViewModel.onVideoPositionChanged(
                    videoPlaybackViewModel.currentIndexOfPlayingVideo.value,
                    newPositionMs,
                )
            },
            modifier = Modifier
                .constrainAs(playerComponent) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        CTAComponent(
            modifier = Modifier
                .constrainAs(ctaComponent) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .width(300.dp)
                .padding(end = 40.dp, bottom = 40.dp)
        )

        MetaContainerComponent(
            video = currentVideo,
            modifier = Modifier
                .constrainAs(metadataComponent) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(ctaComponent.start)
                    width = Dimension.fillToConstraints
                }
                .padding(start = 40.dp, bottom = 40.dp)
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
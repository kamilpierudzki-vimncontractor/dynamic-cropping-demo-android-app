package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.LocalLifecycleOwner
import tv.pluto.dynamic.cropping.android.framework.Video
import tv.pluto.dynamic.cropping.android.framework.ui.DynamicCroppingVideoComponent

@Composable
fun PortraitCardComponent(
    video: Video,
    playbackState: Boolean,
    playbackPositionMs: Long,
    onPlaybackPositionChanged: (Long) -> Unit,
    onVideoEnded: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val height = screenHeight * 0.7f

    ConstraintLayout(
        modifier = modifier
            .padding(start = 24.dp, end = 24.dp)
            .height(height)
            .clip(RoundedCornerShape(16.dp)),
    ) {
        val (videoComponent, metadataComponent) = createRefs()

        DynamicCroppingVideoComponent(
            lifecycleOwner = LocalLifecycleOwner.current,
            video = video,
            playbackState = playbackState,
            playbackPositionMs = playbackPositionMs,
            onPlaybackPositionChanged = onPlaybackPositionChanged,
            onVideoEnded = onVideoEnded,
            modifier = Modifier
                .constrainAs(videoComponent) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxSize(),
            )
        PortraitMetadataComponent(
            video = video,
            modifier = Modifier.constrainAs(metadataComponent) {
                start.linkTo(videoComponent.start)
                end.linkTo(videoComponent.end)
                bottom.linkTo(videoComponent.bottom)
                width = Dimension.fillToConstraints
            },
        )
    }
}
package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
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
fun CardComponent(
    video: Video,
    playbackState: Boolean,
    playbackPositionMs: Long,
    coordinateIndex: Int,
    onPlaybackPositionChanged: (Long) -> Unit,
    onCoordinateIndexConsumed: (Int) -> Unit,
    onVideoEnded: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val maxHeight = screenHeight * 0.75f

    ConstraintLayout(
        modifier = modifier
            .padding(start = 24.dp, end = 24.dp)
            .heightIn(max = maxHeight),
    ) {
        val (videoComponent, metadataComponent) = createRefs()

        DynamicCroppingVideoComponent(
            lifecycleOwner = LocalLifecycleOwner.current,
            video = video,
            playbackState = playbackState,
            playbackPositionMs = playbackPositionMs,
            coordinateIndex = coordinateIndex,
            onPlaybackPositionChanged = onPlaybackPositionChanged,
            onCoordinateIndexConsumed = onCoordinateIndexConsumed,
            onVideoEnded = onVideoEnded,
            modifier = Modifier
                .constrainAs(videoComponent) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)),

            )
        PortraitMetadataComponent(
            title = video.title.value,
            details = video.formattedMetadata(),
            modifier = Modifier.constrainAs(metadataComponent) {
                start.linkTo(videoComponent.start)
                end.linkTo(videoComponent.end)
                bottom.linkTo(videoComponent.bottom)
                width = Dimension.fillToConstraints
            },
        )
    }
}

private fun Video.formattedMetadata(): String =
    "${year.value} ${genre.value} ${rating.value} ${time.value}"
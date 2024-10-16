package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.LocalLifecycleOwner
import tv.pluto.dynamic.cropping.android.framework.Metadata
import tv.pluto.dynamic.cropping.android.framework.ui.DynamicCroppingVideoComponent

@Composable
fun CardComponent(
    staticMetadata: Metadata,
    playbackState: Boolean,
    playbackPositionMs: Long,
    coordinateIndex: Int,
    modifier: Modifier = Modifier,
    onPlaybackPositionChanged: (Long) -> Unit,
    onCoordinateIndexConsumed: (Int) -> Unit,
    onVideoEnded: () -> Unit,
) {
    ConstraintLayout(modifier = modifier) {
        val (videoComponent, metadataComponent) = createRefs()

        DynamicCroppingVideoComponent(
            lifecycleOwner = LocalLifecycleOwner.current,
            staticMetadata = staticMetadata,
            playbackState = playbackState,
            playbackPositionMs = playbackPositionMs,
            coordinateIndex = coordinateIndex,
            modifier = Modifier
                .constrainAs(videoComponent) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .height(500.dp)
                .clip(RoundedCornerShape(16.dp)),
            onPlaybackPositionChanged = onPlaybackPositionChanged,
            onCoordinateIndexConsumed = onCoordinateIndexConsumed,
            onVideoEnded = onVideoEnded,
        )
        MetadataComponent(
            title = staticMetadata.title.value,
            details = staticMetadata.formattedMetadata(),
            modifier = Modifier.constrainAs(metadataComponent) {
                start.linkTo(videoComponent.start)
                end.linkTo(videoComponent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
            },
        )
    }
}

private fun Metadata.formattedMetadata(): String =
    "${year.value} ${genre.value} ${rating.value} ${time.value}"
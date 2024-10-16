package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
    onPlaybackPositionChanged: (Long) -> Unit,
    onCoordinateIndexConsumed: (Int) -> Unit,
    onVideoEnded: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(
        modifier = modifier
            .padding(start = 24.dp, end = 24.dp),
    ) {
        val (videoComponent, metadataComponent) = createRefs()

        DynamicCroppingVideoComponent(
            lifecycleOwner = LocalLifecycleOwner.current,
            staticMetadata = staticMetadata,
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
                .aspectRatio(9f / 16f)
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)),

        )
        MetadataComponent(
            title = staticMetadata.title.value,
            details = staticMetadata.formattedMetadata(),
            modifier = Modifier.constrainAs(metadataComponent) {
                start.linkTo(videoComponent.start)
                end.linkTo(videoComponent.end)
                bottom.linkTo(videoComponent.bottom)
                width = Dimension.fillToConstraints
            },
        )
    }
}

private fun Metadata.formattedMetadata(): String =
    "${year.value} ${genre.value} ${rating.value} ${time.value}"
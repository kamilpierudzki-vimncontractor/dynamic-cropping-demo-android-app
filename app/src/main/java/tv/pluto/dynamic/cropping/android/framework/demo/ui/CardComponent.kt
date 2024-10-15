package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import tv.pluto.dynamic.cropping.android.framework.Metadata

@Composable
fun CardComponent(
    staticMetadata: Metadata,
    playbackState: Boolean,
    initialPlaybackPositionMs: Long,
    modifier: Modifier = Modifier,
    onPlaybackPositionChanged: (Long) -> Unit,
) {
    Box(modifier = modifier) {
        DynamicCroppingVideoComponent(
            lifecycleOwner = LocalLifecycleOwner.current,
            staticMetadata = staticMetadata,
            playbackState = playbackState,
            initialPlaybackPositionMs = initialPlaybackPositionMs,
            modifier = Modifier
                .height(500.dp)
                .clip(RoundedCornerShape(16.dp))
                .align(Alignment.Center),
            onPlaybackPositionChanged = onPlaybackPositionChanged,
        )
        MetadataComponent(
            title = staticMetadata.title.value,
            details = staticMetadata.formattedMetadata(),
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

private fun Metadata.formattedMetadata(): String =
    "${year.value} ${genre.value} ${rating.value} ${time.value}"
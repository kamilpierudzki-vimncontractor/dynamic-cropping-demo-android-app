package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import tv.pluto.dynamic.cropping.android.framework.Video
import tv.pluto.dynamic.cropping.android.framework.demo.ExoPlayerManager

@Composable
fun CardComponent(
    exoPlayerManager: ExoPlayerManager,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        DemoVideoComponent(
            exoPlayerManager = exoPlayerManager,
            modifier = Modifier
                .height(500.dp)
                .clip(RoundedCornerShape(16.dp))
                .align(Alignment.Center),
        )
        MetadataComponent(
            title = exoPlayerManager.video.title.value,
            details = exoPlayerManager.video.formattedMetadata(),
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

private fun Video.formattedMetadata(): String =
    "${year.value} ${genre.value} ${rating.value} ${time.value}"
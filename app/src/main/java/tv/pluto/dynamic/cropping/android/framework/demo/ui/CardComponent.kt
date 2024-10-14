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
import tv.pluto.dynamic.cropping.android.framework.demo.DynamicCroppingPlayerIntegration

@Composable
fun CardComponent(
    dynamicCroppingPlayerIntegration: DynamicCroppingPlayerIntegration,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        DynamicCroppingVideoComponent(
            dynamicCroppingPlayerIntegration = dynamicCroppingPlayerIntegration,
            modifier = Modifier
                .height(500.dp)
                .clip(RoundedCornerShape(16.dp))
                .align(Alignment.Center),
        )
        MetadataComponent(
            title = dynamicCroppingPlayerIntegration.video.title.value,
            details = dynamicCroppingPlayerIntegration.video.formattedMetadata(),
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

private fun Video.formattedMetadata(): String =
    "${year.value} ${genre.value} ${rating.value} ${time.value}"
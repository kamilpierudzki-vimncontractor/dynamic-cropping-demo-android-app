package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tv.pluto.dynamic.cropping.android.framework.Video

private val gradient = listOf(
    Color(0x00000000),
    Color(0xB31B1B1B)
)

@Composable
fun PortraitMetadataComponent(
    video: Video,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.background(brush = Brush.verticalGradient(gradient))) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 24.dp, end = 24.dp, bottom = 24.dp, top = 24.dp)
        ) {
            MetaContainerComponent(video = video)
            Spacer(modifier = Modifier.height(8.dp))
            CTAComponent(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Preview
@Composable
private fun MetadataComponentPreview1() {
    PortraitMetadataComponent(
        Video.IndianaJonesAndTheKingdomOfTheCrystalSkull,
        modifier = Modifier,
    )
}
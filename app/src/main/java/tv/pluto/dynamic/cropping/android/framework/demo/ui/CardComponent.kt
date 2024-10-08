package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tv.pluto.dynamic.cropping.android.R
import tv.pluto.dynamic.cropping.android.framework.demo.ExoPlayerManager
import tv.pluto.dynamic.cropping.android.framework.demo.ExoPlayerManagerV2

@Composable
fun CardComponent(
    exoPlayerManagerV2: ExoPlayerManagerV2,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        VideoComponent(
            exoPlayerManagerV2 = exoPlayerManagerV2,
            modifier = Modifier
                .height(500.dp)
                .clip(RoundedCornerShape(16.dp)),
        )
        /*CollapseButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp),
        )*/
        MetadataComponent(
            title = exoPlayerManagerV2.videoName,
            details = "Drama R 1h 47m",
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomCenter),
        )
    }
}

@Composable
private fun CollapseButton(
    modifier: Modifier = Modifier,
) {
    Icon(
        painter = painterResource(id = R.drawable.icon_zoom_in_map),
        contentDescription = "Collapse",
        tint = Color.White,
        modifier = modifier.drawBehind {
            drawCircle(
                color = Color(0X66000000),
                radius = this.size.maxDimension
            )
        },
    )
}
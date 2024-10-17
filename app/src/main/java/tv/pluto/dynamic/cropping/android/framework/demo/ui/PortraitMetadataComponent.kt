package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PortraitMetadataComponent(
    title: String,
    details: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(start = 24.dp, end = 24.dp, bottom = 24.dp)) {
        MetaContainerComponent(title, details)
        Spacer(modifier = Modifier.height(8.dp))
        CTAComponent(modifier = Modifier.fillMaxWidth())
    }
}

@Preview
@Composable
private fun MetadataComponentPreview1() {
    PortraitMetadataComponent(
        title = "Indiana Jones And The Kingdom Of The Crystal Skull",
        details = "Drama R 1H 47M",
        modifier = Modifier.background(Color.Black),
    )
}
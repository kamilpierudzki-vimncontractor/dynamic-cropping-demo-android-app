package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tv.pluto.dynamic.cropping.android.R
import tv.pluto.dynamic.cropping.android.framework.theme.typography.plutoTVSans10

@Composable
fun BottomBarComponent(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        ItemComponent(
            text = "Explore",
            icon = painterResource(id = R.drawable.icon_explore_solid),
            selected = true,
            modifier = Modifier.weight(1f),
        )
        ItemComponent(
            text = "Live TV",
            icon = painterResource(id = R.drawable.icon_live_tv),
            modifier = Modifier.weight(1f),
        )
        ItemComponent(
            text = "On Demand",
            icon = painterResource(id = R.drawable.icon_on_demand),
            modifier = Modifier.weight(1f),
        )
        ItemComponent(
            text = "Search",
            icon = painterResource(id = R.drawable.icon_search),
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun ItemComponent(
    icon: Painter,
    text: String,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
) {
    Column(
        modifier = modifier.clip(RoundedCornerShape(8.dp))
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = if (selected) {
                Color.White
            } else {
                Color(0xFFA8A8A8)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        Text(
            text = text,
            fontFamily = plutoTVSans10,
            fontWeight = FontWeight.Medium,
            color = if (selected) {
                Color.White
            } else {
                Color(0xFFA8A8A8)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
    }
}

@Preview
@Composable
private fun HomeScreenBottomBarComponentPrev() {
    BottomBarComponent(
        modifier = Modifier.size(400.dp, 200.dp)
    )
}
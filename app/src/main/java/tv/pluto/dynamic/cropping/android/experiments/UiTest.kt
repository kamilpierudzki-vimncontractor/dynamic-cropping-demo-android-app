package tv.pluto.dynamic.cropping.android.experiments

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tv.pluto.dynamic.cropping.android.R

@Preview
@Composable
private fun UiTest() {
    Box(
        modifier = Modifier
            .size(500.dp, 500.dp)
            .background(Color.Red)
    ) {
        Box(
            modifier = Modifier
                .size(200.dp, 400.dp)
                .background(Color.Blue)
                .align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(id = R.drawable.car),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp, 200.dp)
                    .graphicsLayer(clip = false)
                    .background(Color.Green.copy(alpha = 1.0f))
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun CustomLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val placeable = measurables[0].measure(constraints)
        layout(constraints.maxWidth, constraints.maxHeight) {
            placeable.placeRelative(-50, 0) // Przesunięcie o 50 dp w lewo
        }
    }
}
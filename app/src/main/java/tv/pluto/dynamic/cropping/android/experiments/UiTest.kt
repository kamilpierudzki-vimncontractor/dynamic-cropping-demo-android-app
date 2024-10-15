package tv.pluto.dynamic.cropping.android.experiments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
private fun UiTest(modifier: Modifier) {
    ConstraintLayout(modifier = modifier.background(Color.Magenta)) {
        val (videoComponent, metadataComponent) = createRefs()

        VideoComponent(
            modifier = Modifier
                .constrainAs(videoComponent) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .background(Color.Green)
        )

        Spacer(
            modifier = Modifier
                .constrainAs(metadataComponent) {
                    start.linkTo(videoComponent.start)
                    end.linkTo(videoComponent.end)
                    bottom.linkTo(videoComponent.bottom)
                    width = Dimension.fillToConstraints
                }
                .background(Color.Blue.copy(alpha = 0.5f))
                .padding(16.dp)
        )
    }
}

@Composable
private fun VideoComponent(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier
        .size(300.dp, 500.dp)
        .background(Color.Green)
    )
}

@Preview
@Composable
fun UiTestPrev() {
    UiTest(modifier = Modifier.size(500.dp, 500.dp))
}
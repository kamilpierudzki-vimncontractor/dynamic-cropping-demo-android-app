package tv.pluto.dynamic.cropping.android.framework

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.LocalLifecycleOwner
import tv.pluto.dynamic.cropping.android.framework.demo.ui.CTAComponent
import tv.pluto.dynamic.cropping.android.framework.demo.ui.MetaContainerComponent
import tv.pluto.dynamic.cropping.android.framework.demo.ui.SimpleVideoComponent

@Composable
fun LandscapeCardComponent(
    video: Video,
    playbackState: Boolean,
    playbackPositionMs: Long,
    onPlaybackPositionChanged: (Long) -> Unit,
    onVideoEnded: () -> Unit,
) {
    val context = LocalContext.current
    val displayMetrics = context.resources.displayMetrics
    val heightPx = displayMetrics.heightPixels
    val density = LocalDensity.current
    val heightDp = with(density) {
        heightPx.toDp()
    }

    ConstraintLayout(
        modifier = Modifier
            .background(Color.Black)
    ) {
        val (playerComponent, ctaComponent, metadataComponent) = createRefs()

        SimpleVideoComponent(
            lifecycleOwner = LocalLifecycleOwner.current,
            video = video,
            playbackState = playbackState,
            playbackPositionMs = playbackPositionMs,
            onPlaybackPositionChanged = onPlaybackPositionChanged,
            onVideoEnded = onVideoEnded,
            modifier = Modifier
                .constrainAs(playerComponent) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .height(heightDp)
        )

        CTAComponent(
            modifier = Modifier
                .constrainAs(ctaComponent) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .width(300.dp)
                .padding(end = 40.dp, bottom = 40.dp)
        )

        MetaContainerComponent(
            video = video,
            modifier = Modifier
                .constrainAs(metadataComponent) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(ctaComponent.start)
                    width = Dimension.fillToConstraints
                }
                .padding(start = 40.dp, bottom = 40.dp)
        )
    }
}
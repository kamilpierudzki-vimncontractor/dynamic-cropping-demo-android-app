package tv.pluto.dynamic.cropping.android.framework.gallery.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import tv.pluto.dynamic.cropping.android.framework.Video
import tv.pluto.dynamic.cropping.android.framework.ui.DynamicCroppingVideoComponent

@Composable
fun GalleryScreen(
    video: Video,
    onBack: () -> Unit,
) {
    Scaffold { contentPadding ->
        Box(modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
            .padding(16.dp)) {

            DynamicCroppingVideoComponent(
                lifecycleOwner = LocalLifecycleOwner.current,
                video = video,
                playbackState = true,
                playbackPositionMs = 0,
                onPlaybackPositionChanged = {},
                onVideoEnded = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(9f / 16f)
                    .align(Alignment.Center),
            )
        }
    }
    BackHandler(onBack = onBack)
}
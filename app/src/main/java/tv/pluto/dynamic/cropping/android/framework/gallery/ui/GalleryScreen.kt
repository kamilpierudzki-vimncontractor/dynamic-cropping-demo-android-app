package tv.pluto.dynamic.cropping.android.framework.gallery.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
            .fillMaxSize()) {

            val scrollState = rememberScrollState()

            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(16.dp)) {

                Spacer(modifier = Modifier
                    .size(130.dp, 130.dp)
                    .background(Color.Green))

                DynamicCroppingVideoComponent(
                    lifecycleOwner = LocalLifecycleOwner.current,
                    video = video,
                    playbackState = true,
                    playbackPositionMs = 0,
                    onPlaybackPositionChanged = {},
                    onVideoEnded = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(9f / 16f),
                )

                Spacer(modifier = Modifier
                    .size(130.dp, 130.dp)
                    .background(Color.Magenta))
            }
        }
    }
    BackHandler(onBack = onBack)
}
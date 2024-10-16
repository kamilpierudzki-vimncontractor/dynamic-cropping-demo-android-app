package tv.pluto.dynamic.cropping.android.framework.gallery.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import tv.pluto.dynamic.cropping.android.framework.Metadata
import tv.pluto.dynamic.cropping.android.framework.ui.DynamicCroppingVideoComponent

@Composable
fun GalleryScreen(
    metadata: Metadata,
    onBack: () -> Unit,
) {
    Scaffold { contentPadding ->
        Box(modifier = Modifier
            .padding(contentPadding)
            .padding(16.dp)
            .fillMaxSize()) {
            DynamicCroppingVideoComponent(
                lifecycleOwner = LocalLifecycleOwner.current,
                staticMetadata = metadata,
                playbackState = true,
                playbackPositionMs = 0,
                coordinateIndex = 0,
                onPlaybackPositionChanged = {},
                onCoordinateIndexConsumed = {},
                onVideoEnded = {},
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
            )
        }
    }
    BackHandler(onBack = onBack)
}
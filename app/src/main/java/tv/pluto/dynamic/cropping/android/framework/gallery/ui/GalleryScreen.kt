package tv.pluto.dynamic.cropping.android.framework.gallery.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import tv.pluto.dynamic.cropping.android.framework.FixedAspectStyledPlayerView
import tv.pluto.dynamic.cropping.android.framework.gallery.ExoPlayerManager

data class GalleryScreenInput(val coordinates: DoubleArray, val videoResId: Int) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GalleryScreenInput

        if (!coordinates.contentEquals(other.coordinates)) return false
        if (videoResId != other.videoResId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = coordinates.contentHashCode()
        result = 31 * result + videoResId
        return result
    }
}

@Composable
fun GalleryScreen(
    exoPlayerManager: ExoPlayerManager,
    galleryScreenInput: GalleryScreenInput,
    onBack: () -> Unit,
) {
    Scaffold { contentPadding ->
        Box(modifier = Modifier
            .padding(contentPadding)
            .padding(16.dp)
            .fillMaxSize()) {
            GalleryVideoComponent(
                exoPlayerManager = exoPlayerManager,
                galleryScreenInput = galleryScreenInput,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
            )
        }
    }
    BackHandler(onBack = onBack)
}

@Composable
private fun GalleryVideoComponent(
    exoPlayerManager: ExoPlayerManager,
    galleryScreenInput: GalleryScreenInput,
    modifier: Modifier,
) {
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .clipToBounds(),
        factory = { context ->
            FixedAspectStyledPlayerView(context)
                .apply {
                    setAspectRatio(9, 16)
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT
                }
                .also { playerView ->
                    exoPlayerManager.apply {
                        setPlayerView(playerView, galleryScreenInput.coordinates)
                        setMediaItemAndPlay(createMediaItem(galleryScreenInput))
                    }
                }
        },
    )
}

private fun createMediaItem(galleryScreenInput: GalleryScreenInput): MediaItem {
    val uri = RawResourceDataSource.buildRawResourceUri(galleryScreenInput.videoResId)
    return MediaItem.fromUri(uri)
}
package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.runtime.Composable
import tv.pluto.dynamic.cropping.android.framework.VideoPlaybackViewModel

@Composable
fun DemoApp(
    videoPlaybackViewModel: VideoPlaybackViewModel,
    isPortrait: Boolean,
) {
    if (isPortrait) {
        HomeScreen(videoPlaybackViewModel = videoPlaybackViewModel)
    } else {
        PlayerScreen(videoPlaybackViewModel = videoPlaybackViewModel)
    }
}
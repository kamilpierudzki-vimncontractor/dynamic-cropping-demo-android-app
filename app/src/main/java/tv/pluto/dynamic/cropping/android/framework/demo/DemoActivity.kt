package tv.pluto.dynamic.cropping.android.framework.demo

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import tv.pluto.dynamic.cropping.android.framework.VideoPlaybackViewModel
import tv.pluto.dynamic.cropping.android.framework.demo.ui.DemoApp
import tv.pluto.dynamic.cropping.android.framework.theme.StaticCroppingDemoTheme

class DemoActivity : ComponentActivity() {

    private val videoPlaybackViewModel: VideoPlaybackViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(videoPlaybackViewModel)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setupUi()
    }

    private fun setupUi() {
        enableEdgeToEdge()
        setContent {
            StaticCroppingDemoTheme {
                DemoApp(videoPlaybackViewModel = videoPlaybackViewModel)
            }
        }
    }
}
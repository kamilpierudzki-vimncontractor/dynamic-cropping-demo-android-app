package tv.pluto.dynamic.cropping.android.framework.demo

import android.content.res.Configuration
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import tv.pluto.dynamic.cropping.android.framework.VideoPlaybackViewModel
import tv.pluto.dynamic.cropping.android.framework.demo.ui.DemoApp
import tv.pluto.dynamic.cropping.android.framework.theme.StaticCroppingDemoTheme

class DemoActivity : ComponentActivity() {

    private val videoPlaybackViewModel: VideoPlaybackViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(videoPlaybackViewModel)
        val isPortrait = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        if (!isPortrait) {
            hideSystemBars()
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setupUi(isPortrait = isPortrait)
    }

    private fun setupUi(isPortrait: Boolean) {
        enableEdgeToEdge()
        setContent {
            StaticCroppingDemoTheme {
                DemoApp(
                    videoPlaybackViewModel = videoPlaybackViewModel,
                    isPortrait = isPortrait,
                )
            }
        }
    }

    private fun hideSystemBars() {
        WindowCompat.getInsetsController(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}
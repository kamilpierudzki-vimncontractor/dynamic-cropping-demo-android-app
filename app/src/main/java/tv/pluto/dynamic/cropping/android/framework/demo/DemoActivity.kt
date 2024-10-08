package tv.pluto.dynamic.cropping.android.framework.demo

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import tv.pluto.dynamic.cropping.android.framework.demo.ui.DemoApp
import tv.pluto.dynamic.cropping.android.framework.getLocalVideos
import tv.pluto.dynamic.cropping.android.framework.theme.StaticCroppingDemoTheme

class DemoActivity : ComponentActivity() {

    private lateinit var exoPlayerManagers: List<ExoPlayerManager>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        exoPlayerManagers = createPlayerManagers(this)
        exoPlayerManagers.forEach {
            lifecycle.addObserver(it)
        }
        setupUi()
    }

    private fun setupUi() {
        enableEdgeToEdge()
        setContent {
            StaticCroppingDemoTheme {
                DemoApp(
                    exoPlayerManagers = exoPlayerManagers,
                )
            }
        }
    }

    private fun createPlayerManagers(activity: Activity): List<ExoPlayerManager> =
        getLocalVideos().map { video ->
            ExoPlayerManager(
                activity = activity,
                video = video,
            )
        }
}
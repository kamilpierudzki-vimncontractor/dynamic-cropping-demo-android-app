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

    private lateinit var dynamicCroppingPlayerIntegrations: List<DynamicCroppingPlayerIntegration>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        dynamicCroppingPlayerIntegrations = createDynamicCroppingPlayerIntegrations(this)
            .onEach {
                lifecycle.addObserver(it)
            }
        setupUi()
    }

    private fun setupUi() {
        enableEdgeToEdge()
        setContent {
            StaticCroppingDemoTheme {
                DemoApp(
                    dynamicCroppingPlayerIntegrations = dynamicCroppingPlayerIntegrations,
                )
            }
        }
    }

    private fun createDynamicCroppingPlayerIntegrations(activity: Activity): List<DynamicCroppingPlayerIntegration> =
        getLocalVideos().map { video ->
            DynamicCroppingPlayerIntegration(
                activity = activity,
                video = video,
            )
        }
}
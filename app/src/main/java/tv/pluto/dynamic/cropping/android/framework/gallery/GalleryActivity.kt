package tv.pluto.dynamic.cropping.android.framework.gallery

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import tv.pluto.dynamic.cropping.android.framework.gallery.ui.GalleryApp
import tv.pluto.dynamic.cropping.android.framework.theme.StaticCroppingDemoTheme

class GalleryActivity : ComponentActivity() {

    private val dynamicCroppingPlayerManager = DynamicCroppingPlayerManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        lifecycle.addObserver(dynamicCroppingPlayerManager)
        setupUi()
    }

    private fun setupUi() {
        setContent {
            StaticCroppingDemoTheme {
                GalleryApp(
                    dynamicCroppingPlayerManager = dynamicCroppingPlayerManager,
                )
            }
        }
    }
}
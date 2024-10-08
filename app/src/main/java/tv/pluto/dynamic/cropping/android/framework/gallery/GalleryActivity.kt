package tv.pluto.dynamic.cropping.android.framework.gallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import tv.pluto.dynamic.cropping.android.framework.gallery.ui.GalleryApp
import tv.pluto.dynamic.cropping.android.framework.theme.StaticCroppingDemoTheme

class GalleryActivity : ComponentActivity() {

    private val exoPlayerManager = ExoPlayerManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(exoPlayerManager)
        setupUi()
    }

    private fun setupUi() {
        setContent {
            StaticCroppingDemoTheme {
                GalleryApp(
                    exoPlayerManager = exoPlayerManager,
                )
            }
        }
    }
}
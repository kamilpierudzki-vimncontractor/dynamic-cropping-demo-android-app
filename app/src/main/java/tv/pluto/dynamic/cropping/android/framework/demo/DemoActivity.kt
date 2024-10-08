package tv.pluto.dynamic.cropping.android.framework.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import tv.pluto.dynamic.cropping.android.framework.createPlayerManagers
import tv.pluto.dynamic.cropping.android.framework.demo.ui.DemoApp
import tv.pluto.dynamic.cropping.android.framework.theme.StaticCroppingDemoTheme

class DemoActivity : ComponentActivity() {

    private lateinit var exoPlayerManagers: List<ExoPlayerManager>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
}
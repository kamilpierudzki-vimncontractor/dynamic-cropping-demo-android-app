package tv.pluto.dynamic.cropping.android.framework.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import tv.pluto.dynamic.cropping.android.framework.Video
import tv.pluto.dynamic.cropping.android.framework.theme.StaticCroppingDemoTheme

class DemoActivity : ComponentActivity() {

    private val exoPlayerManager = ExoPlayerManagerImpl(this)
    private lateinit var exoPlayerManagers: List<ExoPlayerManagerV2>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(exoPlayerManager)
        exoPlayerManagers = createPlayerManagers()
        exoPlayerManagers.forEach {
            lifecycle.addObserver(it)
        }
        setupUi()
    }

    private fun createPlayerManagers() =
        listOf(
            Video.PainGain,
            Video.GIJoeRetaliation,
            Video.IndianaJonesAndTheKingdomOfTheCrystalSkull,
            Video.IndianaJonesAndTheLastCrusade,
            Video.IndianaJonesAndTheRaidersOfTheLostArk,
            Video.IndianaJonesAndTheTempleOfDoom,
            Video.SavingPrivateRyan,
            Video.Gladiator,
            Video.TerminatorGenisysCoordinates,
        ).map { video ->
            ExoPlayerManagerV2Impl(
                activity = this,
                videoResourceId = video.videoResId,
                videoName = video.name,
                coordinates = video.coordinates,
            )
        }

    private fun setupUi() {
        enableEdgeToEdge()
        setContent {
            StaticCroppingDemoTheme {
                DemoApp(
                    exoPlayerManager = exoPlayerManager,
                    exoPlayerManagers = exoPlayerManagers,
                )
            }
        }
    }
}
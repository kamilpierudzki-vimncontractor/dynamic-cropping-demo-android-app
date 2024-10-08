package tv.pluto.dynamic.cropping.android.framework

import android.app.Activity
import tv.pluto.dynamic.cropping.android.framework.demo.ExoPlayerManager

fun createVideos(): List<Video> =
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
    )

fun createPlayerManagers(activity: Activity): List<ExoPlayerManager> =
    createVideos().map { video ->
        ExoPlayerManager(
            activity = activity,
            videoResourceId = video.videoResId,
            videoName = video.name,
            coordinates = video.coordinates,
        )
    }
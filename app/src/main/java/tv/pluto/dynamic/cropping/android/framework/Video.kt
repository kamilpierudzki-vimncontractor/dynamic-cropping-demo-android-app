package tv.pluto.dynamic.cropping.android.framework

import tv.pluto.dynamic.cropping.android.R
import tv.pluto.dynamic.cropping.android.logic.coords.gIJoeRetaliationCoordinates
import tv.pluto.dynamic.cropping.android.logic.coords.gladiatorCoordinates
import tv.pluto.dynamic.cropping.android.logic.coords.indianaJonesAndTheKingdomOfTheCrystalSkullCoordinates
import tv.pluto.dynamic.cropping.android.logic.coords.indianaJonesAndTheLastCrusadeCoordinates
import tv.pluto.dynamic.cropping.android.logic.coords.indianaJonesAndTheRaidersOfTheLostArkCoordinates
import tv.pluto.dynamic.cropping.android.logic.coords.indianaJonesAndTheTempleOfDoomCoordinates
import tv.pluto.dynamic.cropping.android.logic.coords.painGainCoordinates
import tv.pluto.dynamic.cropping.android.logic.coords.savingPrivateRyanCoordinates
import tv.pluto.dynamic.cropping.android.logic.coords.terminatorGenisysCoordinates

sealed class Video(val name: String, val coordinates: () -> DoubleArray, val videoResId: Int) {

    data object PainGain : Video("Pain Gain", { painGainCoordinates }, R.raw.pain_gain)

    data object GIJoeRetaliation : Video("GI Joe Retaliation", { gIJoeRetaliationCoordinates }, R.raw.gi_joe_retaliation)

    data object IndianaJonesAndTheKingdomOfTheCrystalSkull : Video("Indiana Jones And The Kingdom Of The Crystal Skull",
        { indianaJonesAndTheKingdomOfTheCrystalSkullCoordinates },
        R.raw.indiana_jones_and_the_kingdom_of_the_crystal_skull)

    data object IndianaJonesAndTheLastCrusade : Video("Indiana Jones And The Last Crusade",
        { indianaJonesAndTheLastCrusadeCoordinates },
        R.raw.indiana_jones_and_the_last_crusade)

    data object IndianaJonesAndTheRaidersOfTheLostArk : Video("Indiana Jones And The Raiders Of The Lost Ark",
        { indianaJonesAndTheRaidersOfTheLostArkCoordinates },
        R.raw.indiana_jones_and_the_raiders_of_the_lost_ark)

    data object IndianaJonesAndTheTempleOfDoom : Video("Indiana Jones And The Temple Of Doom",
        { indianaJonesAndTheTempleOfDoomCoordinates },
        R.raw.indiana_jones_and_the_temple_of_doom)

    data object SavingPrivateRyan :
        Video("Saving Private Ryan", { savingPrivateRyanCoordinates }, R.raw.saving_private_ryan)

    data object Gladiator : Video("Gladiator", { gladiatorCoordinates }, R.raw.gladiator)

    data object TerminatorGenisysCoordinates :
        Video("Terminator Genisys", { terminatorGenisysCoordinates }, R.raw.terminator_genisys)
}
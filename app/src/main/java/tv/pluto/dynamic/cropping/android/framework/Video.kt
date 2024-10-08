package tv.pluto.dynamic.cropping.android.framework

import tv.pluto.dynamic.cropping.android.R
import tv.pluto.dynamic.cropping.android.logic.Genre
import tv.pluto.dynamic.cropping.android.logic.Rating
import tv.pluto.dynamic.cropping.android.logic.Time
import tv.pluto.dynamic.cropping.android.logic.Title
import tv.pluto.dynamic.cropping.android.logic.Year
import tv.pluto.dynamic.cropping.android.logic.coords.benchmarkCoordinates
import tv.pluto.dynamic.cropping.android.logic.coords.gIJoeRetaliationCoordinates
import tv.pluto.dynamic.cropping.android.logic.coords.gladiatorCoordinates
import tv.pluto.dynamic.cropping.android.logic.coords.indianaJonesAndTheKingdomOfTheCrystalSkullCoordinates
import tv.pluto.dynamic.cropping.android.logic.coords.indianaJonesAndTheRaidersOfTheLostArkCoordinates
import tv.pluto.dynamic.cropping.android.logic.coords.painAndGainCoordinates
import tv.pluto.dynamic.cropping.android.logic.coords.savingPrivateRyanCoordinates
import tv.pluto.dynamic.cropping.android.logic.coords.snakeEyesGiJoeOriginsCoordinates
import tv.pluto.dynamic.cropping.android.logic.coords.terminator2JudgementDayCoordinates
import tv.pluto.dynamic.cropping.android.logic.coords.transformersRevengeOfTheFallenCoordinates
import tv.pluto.dynamic.cropping.android.logic.coords.transformersRiseOfTheBeastsCoordinates

sealed class Video(
    val title: Title,
    val genre: Genre,
    val year: Year,
    val rating: Rating,
    val time: Time,
    val coordinates: () -> DoubleArray,
    val videoResId: Int,
) {

    data object Benchmark : Video(
        Title("Benchmark"),
        Genre("X"),
        Year("X"),
        Rating("X"),
        Time("X"),
        { benchmarkCoordinates },
        R.raw.benchmark,
    )

    data object Terminator2JudgmentDay : Video(
        Title("Terminator 2: Judgment Day"),
        Genre("Action"),
        Year("1991"),
        Rating("R"),
        Time("2H 17M"),
        { terminator2JudgementDayCoordinates },
        R.raw.terminator_2_judgment_day,
    )

    data object Gladiator : Video(
        Title("Gladiator"),
        Genre("Action"),
        Year("2000"),
        Rating("R"),
        Time("2H 34M"),
        { gladiatorCoordinates },
        R.raw.gladiator,
    )

    data object SnakeEyesGIJoeOrigins : Video(
        Title("Snake Eyes: GI Joe Origins"),
        Genre("Action"),
        Year("2021"),
        Rating("PG-13"),
        Time("2H 1M"),
        { snakeEyesGiJoeOriginsCoordinates },
        R.raw.snake_eyes_gi_joe_origins,
    )

    data object TransformersRevengeOfTheFallen : Video(
        Title("Transformers Revenge of the Fallen"),
        Genre("Action"),
        Year("2009"),
        Rating("PG-13"),
        Time("2H 29M"),
        { transformersRevengeOfTheFallenCoordinates },
        R.raw.transformers_revenge_of_the_fallen,
    )

    data object IndianaJonesAndTheRaidersOfTheLostArk : Video(
        Title("Indiana Jones and the Raiders of the Lost Ark"),
        Genre("Action"),
        Year("1981"),
        Rating("PG-13"),
        Time("1H 55M"),
        { indianaJonesAndTheRaidersOfTheLostArkCoordinates },
        R.raw.indiana_jones_and_the_raiders_of_the_lost_ark,
    )

    data object SavingPrivateRyan : Video(
        Title("Saving Private Ryan"),
        Genre("Drama"),
        Year("1998"),
        Rating("R"),
        Time("2H 49M"),
        { savingPrivateRyanCoordinates },
        R.raw.saving_private_ryan,
    )

    data object GIJoeRetaliation : Video(
        Title("GI Joe Retaliation"),
        Genre("Action"),
        Year("2013"),
        Rating("R"),
        Time("1H 50M"),
        { gIJoeRetaliationCoordinates },
        R.raw.gi_joe_retaliation,
    )

    data object IndianaJonesAndTheKingdomOfTheCrystalSkull : Video(
        Title("Indiana Jones and the Kingdom of the Crystal Skull"),
        Genre("Action"),
        Year("2008"),
        Rating("PG-13"),
        Time("2H 2M"),
        { indianaJonesAndTheKingdomOfTheCrystalSkullCoordinates },
        R.raw.indiana_jones_and_the_kingdom_of_the_crystal_skull,
    )

    data object TransformersRiseOfTheBeastsCoordinates : Video(
        Title("Transformers Rise of the Beasts"),
        Genre("Action"),
        Year("2023"),
        Rating("PG-13"),
        Time("2H 7M"),
        { transformersRiseOfTheBeastsCoordinates },
        R.raw.transformers_rise_of_the_beasts,
    )

    data object PainGain : Video(
        Title("Pain and Gain"),
        Genre("Action"),
        Year("2013"),
        Rating("R"),
        Time("2H 9M"),
        { painAndGainCoordinates },
        R.raw.pain_and_gain,
    )
}
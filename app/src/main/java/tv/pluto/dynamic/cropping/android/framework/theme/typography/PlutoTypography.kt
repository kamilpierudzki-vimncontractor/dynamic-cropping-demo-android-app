package tv.pluto.dynamic.cropping.android.framework.theme.typography

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import tv.pluto.dynamic.cropping.android.R

val plutoTVSans10 = FontFamily(
    Font(R.font.pluto_tv_sans_regular, FontWeight.Normal),
    Font(R.font.pluto_tv_sans_regular_medium, FontWeight.Medium),
    Font(R.font.pluto_tv_sans_semi_bold, FontWeight.SemiBold),
    Font(R.font.pluto_tv_sans_bold, FontWeight.Bold),
    Font(R.font.pluto_tv_sans_extra_bold, FontWeight.ExtraBold),
)

/**
 * This interface holds sum of both Text Styles for mobile and CTV. Some of styles are used just in CTV and some are
 * used just for mobile. This may lead to issues while developing components to be reused between two platforms. It
 * needs to be consulted with Design Team, so 1) text styles are unified and same styles are used between components
 * on mobile and CTV, or 2) some mid layer of text attributes is proposed by Design Team - ANDROID-16634
 */
@Suppress("ComplexInterface")
internal interface PlutoTypography {
    val zetta: TextStyle
    val peta: TextStyle
    val tera: TextStyle
    val giga: TextStyle
    val mega: TextStyle
    val megaSemiBold: TextStyle
    val kilo: TextStyle
    val kiloMedium: TextStyle
    val deka: TextStyle
    val deci: TextStyle
    val deciSemiBold: TextStyle
    val milli: TextStyle
    val microBold: TextStyle
    val micro: TextStyle
    val nano: TextStyle
    val pico: TextStyle
    val femto: TextStyle
    val atto: TextStyle
    val attoMedium: TextStyle
    val zepto: TextStyle
}

/**
 * Pluto typography set for mobile, based on Figma docs, ref: https://zeroheight.com/44530693a/p/6175a1-small--medium-devices
 */
@Immutable
internal data class PlutoTypographyMobile internal constructor(
    override val zetta: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 48.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 56.sp
    ),
    override val peta: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 38.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 44.sp
    ),
    // not implemented for mobile
    override val tera: TextStyle = TextStyle(),
    override val giga: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 40.sp
    ),
    // not implemented for mobile
    override val mega: TextStyle = TextStyle(),
    // not implemented for mobile
    override val megaSemiBold: TextStyle = TextStyle(),
    override val kilo: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 18.sp
    ),
    // not implemented for mobile
    override val kiloMedium: TextStyle = TextStyle(),
    override val deka: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 24.sp
    ),
    override val deci: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 24.sp
    ),
    override val deciSemiBold: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 24.sp
    ),
    override val milli: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 21.sp
    ),
    override val microBold: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 28.sp
    ),
    override val micro: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 22.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 28.sp
    ),
    override val nano: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp
    ),

    // not implemented for mobile
    override val pico: TextStyle = TextStyle(),
    override val femto: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 16.sp
    ),
    override val atto: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 10.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 12.sp
    ),
    override val attoMedium: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 12.sp
    ),
    override val zepto: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 8.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 12.sp
    ),
) : PlutoTypography

/**
 * Pluto typography set for CTV, based on Figma docs, ref: https://zeroheight.com/44530693a/p/359e5e-large-devices
 */
@Immutable
internal data class PlutoTypographyCTV internal constructor(
    override val zetta: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 67.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 76.sp
    ),
    override val peta: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 35.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 40.sp
    ),
    override val tera: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 29.sp
    ),
    override val giga: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 19.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 24.sp
    ),
    override val mega: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 19.5.sp
    ),
    override val megaSemiBold: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 19.5.sp
    ),
    override val kilo: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 17.sp
    ),
    override val kiloMedium: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 17.sp
    ),
    override val deka: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 14.5.sp
    ),
    override val deci: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 17.5.sp
    ),
    override val deciSemiBold: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 17.5.sp
    ),
    // not implemented for ctv
    override val milli: TextStyle = TextStyle(),
    override val microBold: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 14.sp
    ),
    override val micro: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 14.sp
    ),
    // not implemented for ctv
    override val nano: TextStyle = TextStyle(),
    override val pico: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 9.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 10.5.sp
    ),
    override val femto: TextStyle = TextStyle(
        fontFamily = plutoTVSans10,
        fontSize = 8.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 10.5.sp
    ),
    // not implemented for ctv
    override val atto: TextStyle = TextStyle(),
    // not implemented for ctv
    override val attoMedium: TextStyle = TextStyle(),
    // not implemented for ctv
    override val zepto: TextStyle = TextStyle(),
) : PlutoTypography

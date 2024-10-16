package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tv.pluto.dynamic.cropping.android.framework.theme.typography.plutoTVSans10

private val selectedButtonColors = ButtonColors(
    containerColor = Color.Black,
    contentColor = Color.White,
    disabledContainerColor = Color.Black,
    disabledContentColor = Color.White,
)

private val otherButtonColors = ButtonColors(
    containerColor = Color.Transparent,
    contentColor = Color(0x80FFFFFF),
    disabledContainerColor = Color.Transparent,
    disabledContentColor = Color(0x80FFFFFF),
)

@Composable
fun TopSelectionComponent(
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier
            .horizontalScroll(scrollState)
            .padding(top = 18.dp),
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        TextButton(
            onClick = {},
            modifier = Modifier.align(Alignment.CenterVertically),
            colors = otherButtonColors,
        ) {
            Text(
                text = "Popular Now",
                fontFamily = plutoTVSans10,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
            )
        }
        Button(
            onClick = {},
            modifier = Modifier.align(Alignment.CenterVertically),
            colors = selectedButtonColors,
        ) {
            Text(
                text = "You May Also Like",
                fontFamily = plutoTVSans10,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
            )
        }

        TextButton(
            onClick = {},
            modifier = Modifier.align(Alignment.CenterVertically),
            colors = otherButtonColors,
        ) {
            Text(
                text = "Trending",
                fontFamily = plutoTVSans10,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
            )
        }
        TextButton(
            onClick = {},
            modifier = Modifier.align(Alignment.CenterVertically),
            colors = otherButtonColors,
        ) {
            Text(
                text = "Bacause You Watched",
                fontFamily = plutoTVSans10,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
    }
}

@Preview
@Composable
private fun TopBarComponentPrev() {
    TopSelectionComponent(modifier = Modifier.height(83.dp))
}
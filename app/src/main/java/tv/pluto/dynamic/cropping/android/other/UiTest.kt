package tv.pluto.dynamic.cropping.android.other

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
private fun UiTest() {
    Spacer(modifier = Modifier
        .size(500.dp, 500.dp)
        .background(brush = Brush.verticalGradient(listOf(
            Color.DarkGray,
            Color.Black,
            Color.DarkGray,
        )))
    )
}
package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import tv.pluto.dynamic.cropping.android.framework.theme.typography.plutoTVSans10

@Composable
fun MetaContainerComponent(
    title: String,
    details: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier=modifier) {
        Text(
            text = title,
            fontFamily = plutoTVSans10,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.White
        )
        Text(
            text = details,
            fontFamily = plutoTVSans10,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = Color(0x80FFFFFF)
        )
    }
}
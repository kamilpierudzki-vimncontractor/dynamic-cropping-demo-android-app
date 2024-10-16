package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tv.pluto.dynamic.cropping.android.R
import tv.pluto.dynamic.cropping.android.framework.theme.typography.plutoTVSans10

private val watchNowButtonColors = ButtonColors(
    containerColor = Color(0xE6525B61),
    contentColor = Color.White,
    disabledContainerColor = Color(0xE6525B61),
    disabledContentColor = Color.White,
)

@Composable
fun MetadataComponent(
    title: String,
    details: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(start = 24.dp, end = 24.dp, bottom = 24.dp)) {
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
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                onClick = {},
                colors = watchNowButtonColors,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_watch_now),
                    contentDescription = null,
                )
                Text(
                    text = "Watch Now",
                    fontFamily = plutoTVSans10,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    style = TextStyle(
                        lineHeight = 16.sp,
                    ),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 8.dp, bottom = 1.dp),
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.icon_add),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .weight(1f),
                tint = Color.White,
            )
            Icon(
                painter = painterResource(id = R.drawable.icon_info),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.White,
            )
        }
    }
}

@Preview
@Composable
private fun MetadataComponentPreview1() {
    MetadataComponent(
        title = "Indiana Jones And The Kingdom Of The Crystal Skull",
        details = "Drama R 1H 47M",
        modifier = Modifier.background(Color.Black),
    )
}
package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import tv.pluto.dynamic.cropping.android.R
import tv.pluto.dynamic.cropping.android.framework.theme.typography.plutoTVSans10

private val watchNowButtonColors = ButtonColors(
    containerColor = Color(0x1AFFFFFF),
    contentColor = Color.White,
    disabledContainerColor = Color(0x1AFFFFFF),
    disabledContentColor = Color.White,
)

@Composable
fun MetadataComponent(
    modifier: Modifier = Modifier,
    title: String,
    details: String,
) {
    Column(
        modifier = modifier.widthIn(max = 250.dp),
    ) {
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
        Row {
            Button(
                onClick = {},
                colors = watchNowButtonColors,
                modifier = Modifier,
            ) {
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_watch_now),
                        contentDescription = null,
                    )
                    Text(
                        text = "Watch Now",
                        fontFamily = plutoTVSans10,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 8.dp),
                    )
                }
            }
            IconButton(
                onClick = {},
                modifier = Modifier,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_add),
                    contentDescription = null,
                    tint = Color.White,
                )
            }
            IconButton(
                onClick = {},
                modifier = Modifier,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_info),
                    contentDescription = null,
                    tint = Color.White,
                )
            }
        }
    }
}

@Preview
@Composable
private fun MetadataComponentPreview1() {
    Box(modifier = Modifier.size(500.dp, 500.dp)) {
        MetadataComponent(
            title = "Indiana Jones And The Kingdom Of The Crystal Skull",
            details = "Drama R 1h 47m",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.Center)
                .background(Color.Black),
        )
    }
}
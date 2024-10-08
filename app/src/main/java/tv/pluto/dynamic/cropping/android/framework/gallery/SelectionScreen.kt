package tv.pluto.dynamic.cropping.android.framework.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import tv.pluto.dynamic.cropping.android.framework.Video

@Composable
fun SelectionScreen(onClipSelected: (Video) -> Unit) {
    val scrollState = rememberScrollState()

    Scaffold { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
                .verticalScroll(state = scrollState),
        ) {
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
            ).forEach { availableClip ->
                TextButton(
                    onClick = { onClipSelected(availableClip) }
                ) {
                    Text(
                        text = availableClip.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .background(Color.Black)
                        .fillMaxWidth(),
                )
            }
        }
    }
}
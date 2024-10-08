package tv.pluto.dynamic.cropping.android.other

import android.view.LayoutInflater
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import tv.pluto.dynamic.cropping.android.R

@Composable
private fun AviaPlayerComponent(modifier: Modifier = Modifier, onPlayerLayoutInflated: (View) -> Unit) {
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            LayoutInflater.from(context).inflate(R.layout.player_layout, null).also {
                onPlayerLayoutInflated(it)
            }
        })
}
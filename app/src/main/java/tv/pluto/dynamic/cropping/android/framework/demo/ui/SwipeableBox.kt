package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import kotlin.math.absoluteValue
import kotlinx.coroutines.coroutineScope

sealed interface SwipeDirection {
    data object Up : SwipeDirection
    data object Down : SwipeDirection
}

@Composable
fun SwipeableBox(
    onSwipe: (SwipeDirection) -> Unit,
    modifier: Modifier = Modifier,
) {
    var boxHeight by remember { mutableFloatStateOf(0f) }
    var startY by remember { mutableFloatStateOf(0f) }
    var endY by remember { mutableFloatStateOf(0f) }

    Spacer(
        modifier = modifier
            .pointerInput(Unit) {
                coroutineScope {
                    detectVerticalDragGestures(
                        onDragStart = { offset ->
                            startY = offset.y
                        },
                        onDragEnd = {
                            val dragDistance = endY - startY
                            val threshold = boxHeight * 0.025f
                            if (dragDistance.absoluteValue >= threshold) {
                                val direction = if (dragDistance > 0) SwipeDirection.Up else SwipeDirection.Down
                                onSwipe(direction)
                            }
                        },
                        onVerticalDrag = { change, _ ->
                            endY = change.position.y
                        }
                    )
                }
            }
            .onGloballyPositioned { coordinates ->
                boxHeight = coordinates.size.height.toFloat()
            }
    )
}

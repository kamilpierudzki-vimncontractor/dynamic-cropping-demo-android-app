package tv.pluto.dynamic.cropping.android.framework.window

import java.util.LinkedList
import tv.pluto.dynamic.cropping.android.logic.PlayerWindowViewInfoRetriever
import tv.pluto.dynamic.cropping.android.logic.PointsBetweenGenerator
import tv.pluto.dynamic.cropping.android.logic.ViewTranslationX

class PlayerWindowViewSyntheticAnimation(
    private val playerWindowViewInfoRetriever: PlayerWindowViewInfoRetriever,
    private val pointsBetweenGenerator: PointsBetweenGenerator,
    private val viewTranslationX: ViewTranslationX,
    private val targetFrameDurationMs: Double = 16.0,
) {

    private val memory = LinkedList<Runnable>()

    fun cancel() {
        memory.forEach { viewTranslationX.removeCallbacks(it) }
        memory.clear()
    }

    fun start(durationMs: Long, targetValue: Double) {
        val startPosition = playerWindowViewInfoRetriever.translationX
        val numberOfPintsToGenerate = durationMs / targetFrameDurationMs
        val pointsBetween = pointsBetweenGenerator.generate(
            start = startPosition.toDouble(),
            end = targetValue,
            n = numberOfPintsToGenerate.toInt(),
        )
        val absoluteXPositionsInFrame =
            doubleArrayOf(startPosition.toDouble()) + pointsBetween + doubleArrayOf(targetValue)
        val delayForEachMoveMs = ((1.0 * durationMs) / absoluteXPositionsInFrame.size).toLong()
        cancel()
        android.util.Log.d("test123",
            "Animation, start ${viewTranslationX.translationX} -> $targetValue, durationMs=$durationMs, delayForEachMoveMs=$delayForEachMoveMs")
        absoluteXPositionsInFrame
            .map { it.toInt() }
            .forEachIndexed { index, absoluteXPosition ->
                val runnable = Runnable {
                    android.util.Log.d("test123", "Synthetic Animation, inside runnable, point between frames: $absoluteXPosition")
                    viewTranslationX.translationX = absoluteXPosition.toFloat()
                }
                val delayInMs = delayForEachMoveMs * index
                android.util.Log.d("test123", "Synthetic Animation, postDelayed($absoluteXPosition, $delayInMs)")
                viewTranslationX.postDelayed(runnable, delayInMs)
                memory.add(runnable)
            }
    }
}
package tv.pluto.dynamic.cropping.android.framework.window

import android.animation.ObjectAnimator
import android.view.View

class PlayerWindowViewAnimation(
    private val playerWindowView: View,
) {

    private var animator: ObjectAnimator? = null

    fun cancel() {
        animator?.cancel()
        animator?.removeAllListeners()
        animator = null
    }

    fun start(durationMs: Long, targetValue: Int) {
        cancel()
        android.util.Log.d("test123", "Animation, start ${playerWindowView.translationX} -> $targetValue, durationMs=$durationMs")
        animator = ObjectAnimator.ofFloat(playerWindowView, "translationX", targetValue.toFloat()).apply {
            duration = durationMs
            addUpdateListener { v ->
                val lastAnimatorValue = (v.animatedValue as Float)
                android.util.Log.d("test123", "Animation, point between frames: $lastAnimatorValue")
            }
            start()
        }
    }
}
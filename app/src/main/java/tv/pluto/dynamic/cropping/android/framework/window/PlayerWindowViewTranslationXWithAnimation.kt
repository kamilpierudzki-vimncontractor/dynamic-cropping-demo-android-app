package tv.pluto.dynamic.cropping.android.framework.window

import android.animation.ObjectAnimator
import android.view.View
import tv.pluto.dynamic.cropping.android.logic.PlayerWindowView

class PlayerWindowViewTranslationXWithAnimation(private val playerWindowView: View) : PlayerWindowView {

    private var animator: ObjectAnimator? = null

    override val width: Int
        get() = playerWindowView.width
    override val height: Int
        get() = playerWindowView.height

    override fun updateXPosition(xPosition: Float) {
        animateTranslationX(xPosition)
    }

    private fun animateTranslationX(value: Float) {
        animator?.cancel()
        animator?.removeAllListeners()

        android.util.Log.d("test123", "PlayerWindowViewTranslationXWithAnimation, start translation $value")
        animator = ObjectAnimator.ofFloat(playerWindowView, "translationX", value).apply {
            duration = 20
            addUpdateListener { v ->
                val lastAnimatorValue = (v.animatedValue as Float)
                android.util.Log.d("test123", "PlayerWindowViewTranslationXWithAnimation, BETWEEN, $lastAnimatorValue")
            }
            start()
        }
    }
}
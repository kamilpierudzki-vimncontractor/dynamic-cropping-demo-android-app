package tv.pluto.dynamic.cropping.android.framework.window

import android.view.View
import tv.pluto.dynamic.cropping.android.logic.ViewTranslationX

class PlayerWindowViewViewTranslationX(
    private val playerWindowView: View,
) : ViewTranslationX {

    override var translationX: Float
        get() = playerWindowView.translationX
        set(value) {
            playerWindowView.translationX = value
        }

    override fun post(runnable: Runnable) {
        playerWindowView.post(runnable)
    }

    override fun postDelayed(runnable: Runnable, delayMillis: Long) {
        playerWindowView.postDelayed(runnable, delayMillis)
    }

    override fun removeCallbacks(runnable: Runnable) {
        playerWindowView.removeCallbacks(runnable)
    }
}
package tv.pluto.dynamic.cropping.android.framework

import android.view.View
import tv.pluto.dynamic.cropping.android.logic.PlayerWindowView

sealed interface Type {
    data object TranslationXWithSkipping : Type
    data object TranslationX : Type
    data object X : Type
    data object IntX : Type
    data object IntXWithSkipping : Type
}

class PlayerWindowViewFactory {

    fun create(playerWindowView: View, type: Type): PlayerWindowView =
        when (type) {
            Type.TranslationX -> PlayerWindowViewTranslationX(playerWindowView)
            Type.TranslationXWithSkipping -> PlayerWindowViewTranslationXWithSkippingFrames(playerWindowView)
            Type.X -> PlayerWindowViewX(playerWindowView)
            Type.IntX -> PlayerWindowViewIntX(playerWindowView)
            Type.IntXWithSkipping -> PlayerWindowViewIntXWithSkippingFrames(playerWindowView)
        }
}
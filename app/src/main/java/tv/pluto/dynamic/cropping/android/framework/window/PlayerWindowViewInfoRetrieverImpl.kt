package tv.pluto.dynamic.cropping.android.framework.window

import android.view.View
import tv.pluto.dynamic.cropping.android.logic.PlayerWindowViewInfoRetriever

class PlayerWindowViewInfoRetrieverImpl(private val playerWindowView: View) : PlayerWindowViewInfoRetriever {

    override val width: Int
        get() = playerWindowView.width

    override val translationX: Float
        get() = playerWindowView.translationX
}
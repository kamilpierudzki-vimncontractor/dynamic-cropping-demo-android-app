package tv.pluto.dynamic.cropping.android.framework

import android.view.View
import tv.pluto.dynamic.cropping.android.logic.Height
import tv.pluto.dynamic.cropping.android.logic.Size
import tv.pluto.dynamic.cropping.android.logic.Width

class ViewSizeProvider {

    fun getSize(view: View) = Size(Width(view.width), Height(view.height))
}
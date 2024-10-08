package tv.pluto.dynamic.cropping.android.logic

interface PlayerWindowView {

    val width: Int
    val height: Int
    fun updateXPosition(xPosition: Float)
}
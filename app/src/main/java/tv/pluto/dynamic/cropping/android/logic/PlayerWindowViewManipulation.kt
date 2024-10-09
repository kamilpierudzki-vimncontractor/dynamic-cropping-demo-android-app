package tv.pluto.dynamic.cropping.android.logic

interface PlayerWindowViewManipulation {

    fun updateXPosition(absoluteXPosition: Double)
    fun cancelOngoingOperations()
}
package tv.pluto.dynamic.cropping.android.framework

class ListPositionCalculation {

    fun calculatedPosition(requestedIndex: Int, currentIndex: Int): Int =
        when {
            requestedIndex > currentIndex + 1 -> currentIndex + 1
            requestedIndex < currentIndex - 1 -> currentIndex - 1
            else -> requestedIndex
        }
}

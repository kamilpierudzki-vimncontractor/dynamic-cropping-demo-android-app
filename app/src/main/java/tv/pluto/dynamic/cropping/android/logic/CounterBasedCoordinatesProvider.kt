package tv.pluto.dynamic.cropping.android.logic

class CounterBasedCoordinatesProvider(
    private val coordinates: DoubleArray,
    initialCoordinateIndex: Int = 0,
) {

    private var currentIndex = initialCoordinateIndex

    fun getNextCoordinate(): Pair<Double, Int> {
        val consumedIndex = currentIndex
        val coordinate = coordinates[currentIndex]
        currentIndex = (currentIndex + 1) % coordinates.size
        return Pair(coordinate, consumedIndex)
    }
}
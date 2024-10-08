package tv.pluto.dynamic.cropping.android.logic

class InfiniteCoordinatesProvider(
    private val coordinates: DoubleArray,
) {

    private var coordinatesIterator = coordinates.iterator()

    fun getNextCoordinate(): Double {
        return if (coordinatesIterator.hasNext()) {
            coordinatesIterator.nextDouble()
        } else {
            coordinatesIterator = coordinates.iterator()
            getNextCoordinate()
        }
    }
}
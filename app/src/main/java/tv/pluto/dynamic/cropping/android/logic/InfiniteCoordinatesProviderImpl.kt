package tv.pluto.dynamic.cropping.android.logic

class InfiniteCoordinatesProviderImpl(
    private val coordinates: DoubleArray,
) : CoordinatesProvider {

    private var coordinatesIterator = coordinates.iterator()

    override fun getCoordinate(n: Int): Double {
        return if (coordinatesIterator.hasNext()) {
            coordinatesIterator.nextDouble()
        } else {
            coordinatesIterator = coordinates.iterator()
            getCoordinate(n)
        }
    }
}
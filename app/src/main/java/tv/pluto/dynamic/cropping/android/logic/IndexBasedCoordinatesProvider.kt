package tv.pluto.dynamic.cropping.android.logic

class IndexBasedCoordinatesProvider(
    private val coordinates: DoubleArray,
) : CoordinatesProvider {

    private var fallbackCoordinate: Double = 0.0

    override fun getCoordinate(n: Int): Double {
        val indexOnNextFrame = n % coordinates.size
        val nextCoordinate = coordinates.getOrNull(indexOnNextFrame)
        return if (nextCoordinate != null) {
            fallbackCoordinate = nextCoordinate
            nextCoordinate
        } else {
            fallbackCoordinate
        }
    }
}
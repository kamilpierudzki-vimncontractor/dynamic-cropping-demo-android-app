package tv.pluto.dynamic.cropping.android.logic

class PointsBetweenGenerator {

    fun generate(start: Double, end: Double, n: Int): DoubleArray {
        val step = (end - start) / (n + 1)
        return DoubleArray(n) { i -> start + (i + 1) * step }
    }
}
package tv.pluto.dynamic.cropping.android.logic

interface ViewTranslationX {

    var translationX: Float
    fun post(runnable: Runnable)
    fun postDelayed(runnable: Runnable, delayMillis: Long)
    fun removeCallbacks(runnable: Runnable)
}
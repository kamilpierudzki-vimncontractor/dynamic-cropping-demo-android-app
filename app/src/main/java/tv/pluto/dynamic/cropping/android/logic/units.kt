package tv.pluto.dynamic.cropping.android.logic

@JvmInline
value class Width(val value: Int)

@JvmInline
value class Height(val value: Int)

data class VideoResolution(val width: Width, val height: Height)

data class TextureSize(val width: Width, val height: Height)

data class TextureViewSize(val width: Width, val height: Height)

@JvmInline
value class Title(val value: String)

@JvmInline
value class Genre(val value: String)

@JvmInline
value class Year(val value: String)

@JvmInline
value class Rating(val value: String)

@JvmInline
value class Time(val value: String)
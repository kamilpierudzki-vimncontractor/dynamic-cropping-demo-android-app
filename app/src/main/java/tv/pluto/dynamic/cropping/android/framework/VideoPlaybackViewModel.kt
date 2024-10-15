package tv.pluto.dynamic.cropping.android.framework

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel

class VideoPlaybackViewModel : ViewModel(), DefaultLifecycleObserver {

    val metadatas: List<Metadata> = LocalVideos

    private val _videoPlayingStates = mutableStateOf(List(metadatas.size) { i -> Pair(i, false) }.toMap())
    val videoPlayingStates: State<Map<Int, Boolean>> = _videoPlayingStates

    private val _videoPositionStates = mutableStateOf(List(metadatas.size) { i -> Pair(i, 0L) }.toMap())
    val videoPositionStates: State<Map<Int, Long>> = _videoPositionStates

    private val _indexOfCurrentlyPlayingVideo = mutableIntStateOf(0)
    val indexOfCurrentlyPlayingVideo: State<Int> = _indexOfCurrentlyPlayingVideo

    fun onIndexOfPlayingComponentChanged(indexOfPlayingComponent: Int) {
        _videoPlayingStates.value = _videoPlayingStates.value.toMutableMap().apply {
            keys.forEach { put(it, false) }
            put(indexOfPlayingComponent, true)
        }
    }

    fun onVideoPositionChanged(indexOfPlayingComponent: Int, newPosition: Long) {
        _videoPositionStates.value = _videoPositionStates.value.toMutableMap().apply {
            set(indexOfPlayingComponent, newPosition)
        }
        _indexOfCurrentlyPlayingVideo.intValue = indexOfPlayingComponent
    }
}
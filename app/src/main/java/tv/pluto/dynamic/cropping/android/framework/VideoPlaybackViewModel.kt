package tv.pluto.dynamic.cropping.android.framework

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel

class VideoPlaybackViewModel : ViewModel(), DefaultLifecycleObserver {

    val videos: List<Video> = LocalVideos

    private val _videoPlayingStates = mutableStateOf(List(videos.size) { i -> Pair(i, false) }.toMap())
    val videoPlayingStates: State<Map<Int, Boolean>> = _videoPlayingStates

    private val _videoPositionStates = mutableStateOf(List(videos.size) { i -> Pair(i, 0L) }.toMap())
    val videoPositionStates: State<Map<Int, Long>> = _videoPositionStates

    private val _consumedCoordinateIndicesStates = mutableStateOf(List(videos.size) { i -> Pair(i, 0) }.toMap())
    val consumedCoordinateIndicesStates: State<Map<Int, Int>> = _consumedCoordinateIndicesStates

    private val _currentIndexOfPlayingVideo = mutableIntStateOf(0)
    val currentIndexOfPlayingVideo: State<Int> = _currentIndexOfPlayingVideo

    private val _currentVideo = mutableStateOf<Video>(Video.Empty)
    val currentVideo: State<Video> = _currentVideo

    private val _currentVideoPlayingState = mutableStateOf(false)
    val currentVideoPlaybackState: State<Boolean> = _currentVideoPlayingState

    private val _currentPlaybackPositionState = mutableLongStateOf(0L)
    val currentPlaybackPositionState: State<Long> = _currentPlaybackPositionState

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

        _currentIndexOfPlayingVideo.intValue = indexOfPlayingComponent
        _currentVideo.value = videos[indexOfPlayingComponent]
        _currentVideoPlayingState.value = videoPlayingStates.value[indexOfPlayingComponent] ?: false
        _currentPlaybackPositionState.longValue = videoPositionStates.value[indexOfPlayingComponent] ?: 0L
    }

    fun onCoordinateIndexConsumed(indexOfPlayingComponent: Int, consumedIndexOfCoordinate: Int) {
        _consumedCoordinateIndicesStates.value = _consumedCoordinateIndicesStates.value.toMutableMap().apply {
            set(indexOfPlayingComponent, consumedIndexOfCoordinate + 1)
        }
    }
}
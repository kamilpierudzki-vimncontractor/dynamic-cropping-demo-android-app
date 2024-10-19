package tv.pluto.dynamic.cropping.android.framework

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel

class VideoPlaybackViewModel : ViewModel(), DefaultLifecycleObserver {

    val videos: List<Video> = LocalVideos

    private val _videoPlayingStates: MutableState<Map<Int, Boolean>> =
        mutableStateOf(List(videos.size) { i -> Pair(i, false) }.toMap())
    val videoPlayingStates: State<Map<Int, Boolean>> = _videoPlayingStates

    private val _videoPositionStates: MutableState<Map<Int, Long>> =
        mutableStateOf(List(videos.size) { i -> Pair(i, 0L) }.toMap())
    val videoPositionStates: State<Map<Int, Long>> = _videoPositionStates

    private val _currentIndexOfPlayingVideo = mutableIntStateOf(0)
    val currentIndexOfPlayingVideo: State<Int> = _currentIndexOfPlayingVideo

    private val _currentVideo = mutableStateOf<Video>(Video.Empty)
    val currentVideo: State<Video> = _currentVideo

    private val _currentVideoPlaying = mutableStateOf(false)
    val currentVideoPlayback: State<Boolean> = _currentVideoPlaying

    private val _currentPlaybackPosition = mutableLongStateOf(0L)
    val currentPlaybackPosition: State<Long> = _currentPlaybackPosition

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
        _currentVideoPlaying.value = _videoPlayingStates.value[indexOfPlayingComponent] ?: false
        _currentPlaybackPosition.longValue = _videoPositionStates.value[indexOfPlayingComponent] ?: 0L
    }
}
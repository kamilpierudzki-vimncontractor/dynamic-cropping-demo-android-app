package tv.pluto.dynamic.cropping.android.framework

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel

class VideoPlaybackViewModel : ViewModel(), DefaultLifecycleObserver {

    val metadatas: List<Metadata> = LocalVideos

    private val _videoPlayingStates = mutableStateOf(List(metadatas.size) { i -> Pair(i, false) }.toMap())
    val videoPlayingStates: State<Map<Int, Boolean>> = _videoPlayingStates

    private val _videoPositionStates = mutableStateOf(List(metadatas.size) { i -> Pair(i, 0L) }.toMap())
    val videoPositionStates: State<Map<Int, Long>> = _videoPositionStates

    private val _currentlyIndexOfPlayingVideo = mutableIntStateOf(0)
    val currentIndexOfPlayingVideo: State<Int> = _currentlyIndexOfPlayingVideo

    private val _currentMetadata = mutableStateOf<Metadata>(Metadata.Empty)
    val currentMetadata: State<Metadata> = _currentMetadata

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
        _currentlyIndexOfPlayingVideo.intValue = indexOfPlayingComponent
        _currentMetadata.value = metadatas[indexOfPlayingComponent]
        _currentVideoPlayingState.value = videoPlayingStates.value[indexOfPlayingComponent] ?: false
        _currentPlaybackPositionState.longValue = videoPositionStates.value[indexOfPlayingComponent] ?: 0L

        android.util.Log.d("test-seeking", "VideoPlaybackViewModel, update, index: $indexOfPlayingComponent, ${currentMetadata.value.title.value} value: $newPosition")
    }
}
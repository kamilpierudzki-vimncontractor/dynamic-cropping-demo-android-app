package tv.pluto.dynamic.cropping.android.framework.demo.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import tv.pluto.dynamic.cropping.android.framework.VideoPlaybackViewModel

sealed class NavigationScreen(val name: String) {
    data object Home : NavigationScreen("demo_home")
    data object Player : NavigationScreen("demo_player")
}

@Composable
fun DemoApp(videoPlaybackViewModel: VideoPlaybackViewModel) {
    val navController: NavHostController = rememberNavController()

    DemoAppNavHost(
        navController = navController,
        videoPlaybackViewModel = videoPlaybackViewModel,
    )
}

@Composable
fun DemoAppNavHost(
    navController: NavHostController,
    videoPlaybackViewModel: VideoPlaybackViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationScreen.Home.name,
    ) {
        composable(NavigationScreen.Home.name) {
            HomeScreen(
                videoPlaybackViewModel = videoPlaybackViewModel,
            )
        }
        composable(NavigationScreen.Player.name) {
            // todo
        }
    }
}
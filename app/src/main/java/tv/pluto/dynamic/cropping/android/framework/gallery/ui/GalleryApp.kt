package tv.pluto.dynamic.cropping.android.framework.gallery.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import tv.pluto.dynamic.cropping.android.framework.LocalVideos

sealed class NavigationScreen(val route: String) {
    data object Selection : NavigationScreen("selection")
    data object Gallery : NavigationScreen("video/{title}") {

        fun passTitle(title: String): String {
            return "video/$title"
        }
    }
}

@Composable
fun GalleryApp() {
    val navController: NavHostController = rememberNavController()
    GalleryAppNavHost(navController = navController)
}

@Composable
private fun GalleryAppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationScreen.Selection.route,
    ) {
        composable(NavigationScreen.Selection.route) {
            SelectionScreen(
                onVideoSelected = { metadata ->
                    navController.navigate(NavigationScreen.Gallery.passTitle(metadata.title.value))
                }
            )
        }

        composable(
            route = NavigationScreen.Gallery.route,
            arguments = listOf(navArgument("title") { type = NavType.StringType }),
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title")
            GalleryScreen(
                video = LocalVideos.first { it.title.value == title },
                onBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}
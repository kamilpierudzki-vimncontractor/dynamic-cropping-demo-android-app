package tv.pluto.dynamic.cropping.android.framework.gallery.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import tv.pluto.dynamic.cropping.android.framework.gallery.DynamicCroppingPlayerManager

sealed class NavigationScreen(val name: String) {
    data object Selection : NavigationScreen("selection")
    data object Gallery : NavigationScreen("clip")
}

@Composable
fun GalleryApp(
    dynamicCroppingPlayerManager: DynamicCroppingPlayerManager,
) {
    val navController: NavHostController = rememberNavController()
    GalleryAppNavHost(
        navController = navController,
        dynamicCroppingPlayerManager = dynamicCroppingPlayerManager,
    )
}

@Composable
private fun GalleryAppNavHost(
    navController: NavHostController,
    dynamicCroppingPlayerManager: DynamicCroppingPlayerManager,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationScreen.Selection.name,
    ) {
        composable(NavigationScreen.Selection.name) {
            SelectionScreen(
                onVideoSelected = { video ->
                    val galleryScreenInput = GalleryScreenInput(
                        coordinates = video.coordinates(),
                        videoResId = video.videoResId,
                    )
                    val json = Gson().toJson(galleryScreenInput)
                    navController.navigate(NavigationScreen.Gallery.name + "/$json")
                }
            )
        }

        composable(NavigationScreen.Gallery.name + "/{galleryScreenInput}") { backStackEntry ->
            val json = backStackEntry.arguments?.getString("galleryScreenInput")
            val galleryScreenInput = Gson().fromJson(json, GalleryScreenInput::class.java)
            GalleryScreen(
                dynamicCroppingPlayerManager = dynamicCroppingPlayerManager,
                galleryScreenInput = galleryScreenInput,
                onBack = {
                    dynamicCroppingPlayerManager.destroy()
                    navController.navigateUp()
                }
            )
        }
    }
}
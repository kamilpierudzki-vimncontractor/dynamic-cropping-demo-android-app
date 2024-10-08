pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://nexus.pluto.tv/repository/pluto-android")
            credentials {
                username = "android.dev"
                password = "0qUXrWdSPu79uKSrsPuKuGL4KlraVO2"
            }
        }
    }
}

rootProject.name = "Dynamic Cropping Demo App"
include(":app")
 
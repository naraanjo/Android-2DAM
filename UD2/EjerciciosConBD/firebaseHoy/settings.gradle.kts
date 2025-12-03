// settings.gradle.kts

pluginManagement {
    repositories {
        // Repositorios para plugins de Gradle
        google {
            content {
                includeGroupByRegex("com\\.android.*")   // Android Gradle Plugin
                includeGroupByRegex("com\\.google.*")    // Google plugins
                includeGroupByRegex("androidx.*")        // AndroidX plugins
            }
        }
        mavenCentral()        // Plugins en Maven Central
        gradlePluginPortal()  // Portal de plugins oficial de Gradle
    }
}

dependencyResolutionManagement {
    // Solo usar repositorios definidos aquí, no en build.gradle de módulos
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        google()       // Firebase y librerías de Google
        mavenCentral() // Otras librerías
    }
}

rootProject.name = "firebaseHoy"
include(":app")

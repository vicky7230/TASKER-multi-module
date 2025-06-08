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
    }
}

rootProject.name = "Tasker"
include(":app")
include(":feature:notes:data")
include(":feature:notes:domain")
include(":feature:notes:ui")
include(":feature:add_edit_note:data")
include(":feature:add_edit_note:domain")
include(":feature:add_edit_note:ui")
include(":core:network")
include(":core:common")
include(":core:feature_api")
include(":core:database")

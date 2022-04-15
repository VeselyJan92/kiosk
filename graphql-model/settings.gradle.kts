rootProject.name = "graphql-model"

pluginManagement {
    val kotlinVersion: String by settings
    val kspVersion: String by settings
    plugins {
        id("com.google.devtools.ksp") version kspVersion apply false
        kotlin("multiplatform") version kotlinVersion apply false
    }
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}


include(":ksp")




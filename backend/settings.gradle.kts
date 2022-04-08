pluginManagement {
    val kotlinVersion: String by settings
    val kspVersion: String by settings
    plugins {
        id("org.jetbrains.kotlin.plugin.serialization") version kotlinVersion
        id("com.google.devtools.ksp") version kspVersion apply false
        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("jvm") version kotlinVersion
    }
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "backend"

include(":graphql-common-model")
include(":graphql-common-ksp")
include(":ktor-backend")

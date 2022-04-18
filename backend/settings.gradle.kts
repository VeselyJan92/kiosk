rootProject.name = "backend"

/*
pluginManagement {
    val kotlin_version: String by settings
    plugins {
        kotlin("jvm") version kotlin_version
        id("org.jetbrains.kotlin.plugin.serialization") version kotlin_version
    }
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}
*/


include(":ktor")
include(":graphql-client")

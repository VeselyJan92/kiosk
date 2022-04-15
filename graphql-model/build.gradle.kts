plugins {
    kotlin("multiplatform")
    id("com.google.devtools.ksp")

    kotlin("plugin.serialization") version "1.6.20"
}

group = "cz.cvut.veselj57.dt"
version = "1.0"


kotlin {
    jvm {
        withJava()

    }
    js(IR) {
        moduleName = "kiosk-graphql"
        browser()
        useCommonJs()
        binaries.library()

    }
    sourceSets {

        val commonMain by getting{
            dependencies{
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

            }
        }

        val jvmMain by getting{
            kotlin.srcDirs(
                file("$buildDir/generated/ksp/jvm/jvmMain/kotlin"),
            )

            dependencies{
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:1.3.2")
                implementation("io.ktor:ktor-client-core:2.0.0")
                implementation("io.ktor:ktor-client-cio:2.0.0")
            }

        }

        val jsMain by getting{
            kotlin.srcDirs(
                file("$buildDir/generated/ksp/js/jsMain/kotlin"),
            )
        }
    }
}

dependencies {
    //add("kspJvm", project(":ksp"))
    add("kspJs", project(":ksp"))
}

repositories {
    mavenCentral()
}




subprojects {
    repositories {
        mavenCentral()
    }
}



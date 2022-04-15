plugins {
    kotlin("multiplatform")
    id("com.google.devtools.ksp")
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


        val jvmMain by getting{
            kotlin.srcDirs(
                file("$buildDir/generated/ksp/jvm/jvmMain/kotlin"),
            )

            dependencies{
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:1.3.2")
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
    add("kspJvm", project(":ksp"))
    add("kspJs", project(":ksp"))
}

repositories {
    mavenCentral()
}





/*
plugins {
    kotlin("multiplatform") apply false
}

group = "cz.cvut.veselj57.dt.graphql.model"
version = "1.0"
*/


subprojects {
    repositories {
        mavenCentral()
    }
}



plugins {
    kotlin("multiplatform")
    id("com.google.devtools.ksp")

    //id("io.github.turansky.kfc.webpack") version "5.8.0"
}


kotlin {
    jvm {
        withJava()
    }
    js(IR) {
        //useCommonJs()
        binaries.executable()
    }
    sourceSets {
        val commonMain by getting

        val jvmMain by getting{
            kotlin.srcDirs(
                file("$buildDir/generated/ksp/jvm/jvmMain/kotlin"),
            )
        }

        val jsMain by getting{
            kotlin.srcDirs(
                file("$buildDir/generated/ksp/js/jsMain/kotlin"),
            )
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", project(":graphql-common-ksp"))
    add("kspJvm", project(":graphql-common-ksp"))
    add("kspJvmTest", project(":graphql-common-ksp"))
    //add("kspJs", project(":test-processor"))
    //add("kspJsTest", project(":test-processor"))
}

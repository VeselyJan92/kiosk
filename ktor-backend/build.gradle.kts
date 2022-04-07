val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project



plugins {
    application
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.10"

}

group = "cz.cvut.veselj57.dt"
version = "0.0.1"
application {
    mainClass.set("cz.cvut.veselj57.dt.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }

    maven { url = uri("https://dl.bintray.com/kodein-framework/Kodein-DI/") }


}


dependencies {

    implementation("cz.cvut.veselj57.dt:graphql-common:1.0")


    implementation("at.favre.lib:bcrypt:0.9.0")
    implementation("ch.qos.logback:logback-classic:1.2.11")


    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-cio-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-cors:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")


    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")

    testImplementation("io.ktor:ktor-client-serialization:$ktor_version")



    implementation("org.litote.kmongo:kmongo:4.5.0")
    implementation("org.litote.kmongo:kmongo-coroutine-serialization:4.5.0")
    implementation("org.litote.kmongo:kmongo-id-serialization:4.5.0")


    implementation("com.expediagroup:graphql-kotlin-server:6.0.0-alpha.0")
    implementation("com.expediagroup:graphql-kotlin-schema-generator:6.0.0-alpha.0")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")


    val koin_version= "3.2.0-beta-1"
    implementation( "io.insert-koin:koin-core:$koin_version")
   //     implementation( "io.insert-koin:koin-ktor:$koin_version")
    implementation( "io.insert-koin:koin-logger-slf4j:$koin_version")
    testImplementation( "io.insert-koin:koin-test:$koin_version")



    implementation("com.thedeanda:lorem:2.1")


}
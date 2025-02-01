plugins {
    kotlin("jvm") version libs.versions.kotlinVersion
    id("io.ktor.plugin") version libs.versions.ktorVersion
    id("org.jetbrains.kotlin.plugin.serialization") version libs.versions.kotlinVersion
}

group = "com.minin"
version = "0.0.1"

application {
    mainClass.set("com.minin.Starter.kt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://packages.confluent.io/maven/") }
}

dependencies {
    implementation(rootProject.libs.bundles.ktor)
    implementation(rootProject.libs.bundles.db)
    implementation(rootProject.libs.bundles.tools)
    implementation(rootProject.libs.amazonS3)
    testImplementation(rootProject.libs.ktorServerTestHost)
    testImplementation(rootProject.libs.junitTests)
}

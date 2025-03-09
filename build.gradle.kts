import io.ktor.plugin.features.*

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

ktor {
    docker {
        val username = System.getenv("DOCKER_USERNAME")
        val password = System.getenv("DOCKER_PASSWORD")
        localImageName.set("$username/team_space")
        imageTag.set("latest")
        externalRegistry.set(
            DockerImageRegistry.dockerHub(
                appName = provider { "team_space" },
                username = provider { username },
                password = provider { password }
            )
        )
    }
}

tasks.register("buildAndPushDocker") {
    group = "docker"
    description = "Собирает проект, создает Docker-образ и отправляет его в реестр"

    dependsOn("clean", "buildImage", "publishImage")
}


dependencies {
    implementation(rootProject.libs.bundles.ktor)
    implementation(rootProject.libs.bundles.db)
    implementation(rootProject.libs.bundles.tools)
    implementation(rootProject.libs.amazonS3)
    implementation("org.apache.commons:commons-email:1.6.0")

    testImplementation(rootProject.libs.ktorServerTestHost)
    testImplementation(rootProject.libs.junitTests)
}

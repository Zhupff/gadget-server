pluginManagement {
    includeBuild("gradle")
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://jitpack.io")
    }

    plugins {
        kotlin("jvm").version(extra["kotlin.version"] as String)
        kotlin("plugin.spring").version(extra["kotlin.version"] as String)
        id("org.jetbrains.compose").version(extra["compose.version"] as String)
        id("org.springframework.boot").version(extra["springframework.version"] as String)
        id("io.spring.dependency-management").version(extra["springdependency.version"] as String)
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "gadget-server"
include("common")
include("database")
include("client")
include("server")

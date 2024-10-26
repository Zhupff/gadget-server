plugins {
    kotlin("jvm")
    kotlin("plugin.spring") apply false
    id("org.jetbrains.compose") apply false
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management") apply false
}
repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://jitpack.io")
}

dependencies {
}

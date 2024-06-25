plugins {
    kotlin("jvm")
    id("gadget.publish")
}

repositories {
    mavenCentral()
}

dependencies {
}

kotlin {
    jvmToolchain(17)
}
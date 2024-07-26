plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("gadget.script")
}

repositories {
    mavenCentral()
}

dependencies {
    gson()
    autoService()
    implementation(project(":common"))
}

kotlin {
    jvmToolchain(17)
}
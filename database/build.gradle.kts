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
    implementation(project(":model"))
}

kotlin {
    jvmToolchain(17)
}
plugins {
    kotlin("jvm")
    id("gadget.script")
}

repositories {
    mavenCentral()
}

dependencies {
}

kotlin {
    jvmToolchain(17)
}


script {
    publish()
}
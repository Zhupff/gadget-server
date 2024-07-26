plugins {
    kotlin("jvm")
    id("gadget.script")
}

repositories {
    mavenCentral()
}

dependencies {
    gson()
}

kotlin {
    jvmToolchain(17)
}


//script {
//    publish()
//}
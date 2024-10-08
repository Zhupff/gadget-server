plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("gadget.script")
}

dependencies {
    gson()
    autoService()
    implementation(project(":common"))
}
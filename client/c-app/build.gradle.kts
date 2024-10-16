plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("org.jetbrains.compose")
    id("gadget.script")
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    autoService()
    coroutines()
    jcodec()
    zxing()

    implementation(project(":common"))
    implementation(project(":client"))
    implementation(project(":server"))
}
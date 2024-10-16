import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("gadget.script")
    id("org.jetbrains.compose")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://jitpack.io")
    }
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    coroutines()

    implementation(project(":common"))
    implementation(project(":client:c-app"))

    implementation(project(":server:s-app"))
    implementation(project(":server:s-home"))
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(
//                TargetFormat.AppImage,
//                TargetFormat.Deb,
                TargetFormat.Dmg,
                TargetFormat.Exe,
//                TargetFormat.Msi,
//                TargetFormat.Pkg,
//                TargetFormat.Rpm,
            )
            packageName = "gadget-server"
            packageVersion = "1.0.0"
        }
    }
}

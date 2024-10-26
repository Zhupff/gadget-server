import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id("gadget.script.client")
}

dependencies {
    api(project(":client:c-api"))
    implementation(project(":server"))

    coroutines()
    zxing()
}


compose.desktop {
    application {
        mainClass = "zhupff.gadget.client.GadgetClientApplicationKt"

        nativeDistributions {
            windows {
                includeAllModules = true
            }
            targetFormats(
//                TargetFormat.AppImage,
                TargetFormat.Deb,
                TargetFormat.Dmg,
                TargetFormat.Exe,
//                TargetFormat.Msi,
//                TargetFormat.Pkg,
                TargetFormat.Rpm,
            )
            packageName = "gadget-server"
            packageVersion = "1.0.0"
        }
    }
}
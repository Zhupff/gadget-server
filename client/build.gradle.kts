import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.text.SimpleDateFormat

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
            packageName = "GadgetServer"
            packageVersion = SimpleDateFormat("YY.M.d")
                .format(System.currentTimeMillis())
                .also { println("version: $it") }
            includeAllModules = true
            copyright = "© 2024 Zhupff. All rights reserved."
            vendor = "Zhupff"
            licenseFile.set(rootProject.file("LICENSE"))
            targetFormats(
//                TargetFormat.AppImage,
                TargetFormat.Deb,
                TargetFormat.Dmg,
                TargetFormat.Exe,
//                TargetFormat.Msi,
//                TargetFormat.Pkg,
                TargetFormat.Rpm,
            )

            windows {
                shortcut = true
            }
            linux {
                shortcut = true
            }
        }
    }
}
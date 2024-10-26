plugins {
    id("gadget.script.server")
    id("org.springframework.boot")
}

dependencies {
    api(project(":server:s-api"))
    implementation(project(":server:s-home"))
//    implementation(project(":server:s-sqlite"))

    coroutines()
}

springBoot {
    mainClass = "zhupff.gadget.server.GadgetServerApplicationKt"
}
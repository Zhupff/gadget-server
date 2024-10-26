plugins {
    id("gadget.script.server")
    id("org.springframework.boot")
}

dependencies {
    api(project(":server:s-api"))
    implementation(project(":server:s-home"))

    coroutines()
}

springBoot {
    mainClass = "zhupff.gadget.server.GadgetServerApplicationKt"
}
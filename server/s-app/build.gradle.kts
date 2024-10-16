plugins {
    id("gadget.script")
}

script {
    server()
}

dependencies {
    autoService()
    coroutines()

    implementation(project(":basic"))
    implementation(project(":server"))
}
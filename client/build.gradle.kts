plugins {
    id("gadget.script")
}

script {
    client()
}

dependencies {
    coroutines()

    implementation(project(":basic"))
}

plugins {
    id("gadget.script")
}

script {
    client()
}

dependencies {
    autoService()
    coroutines()
    zxing()

    implementation(project(":basic"))
    implementation(project(":client"))
    implementation(project(":server"))
}
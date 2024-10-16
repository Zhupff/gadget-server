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

    implementation(project(":common"))
    implementation(project(":client"))
    implementation(project(":server"))
}
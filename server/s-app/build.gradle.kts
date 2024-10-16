plugins {
    id("gadget.script")
}

script {
    server()
}

dependencies {
    autoService()
    coroutines()
    gson()

    implementation(project(":server"))
    implementation(project(":common"))
    implementation(project(":database"))
}
plugins {
    id("gadget.script")
}

script {
    server()
}

dependencies {
    autoService()

    implementation(project(":basic"))
    implementation(project(":database"))
    implementation(project(":server"))
}
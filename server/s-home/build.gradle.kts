plugins {
    id("gadget.script")
}

script {
    server()
}

dependencies {
    autoService()

    implementation(project(":server"))
}
plugins {
    id("gadget.script")
}

dependencies {
    coroutines()

    implementation(project(":common"))
    implementation(project(":database"))
}
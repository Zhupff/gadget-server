plugins {
    id("gadget.script")
}

dependencies {
    json()
    implementation(project(":basic"))
    implementation(project(":database"))
}
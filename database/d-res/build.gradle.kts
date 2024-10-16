plugins {
    id("gadget.script")
}

dependencies {
    autoService()
    implementation(project(":basic"))
    implementation(project(":database"))
}
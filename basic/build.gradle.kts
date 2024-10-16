plugins {
    id("gadget.script")
}

dependencies {
    gson()
    json()
    jcodec()
    implementation(project(":database"))
}
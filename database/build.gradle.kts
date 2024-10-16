plugins {
    id("gadget.script")
}

dependencies {
    gson()
    autoService()
    implementation(project(":common"))
}
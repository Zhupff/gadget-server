plugins {
    id("gadget.script.server")
}

dependencies {
    implementation(project(":server:s-api"))

    autoService()
    gson()
    javacv()
    jcodec()
}
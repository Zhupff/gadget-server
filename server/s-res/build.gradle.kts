plugins {
    id("gadget.script.server")
}

dependencies {
    implementation(project(":server:s-api"))

    autoService()
    implementation("org.bytedeco:javacv-platform:1.5.10")
}
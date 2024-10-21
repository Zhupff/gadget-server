plugins {
    id("gadget.script")
}

dependencies {
    autoService()
    jcodec()
    implementation(project(":basic"))
    implementation(project(":database"))
    implementation("com.squareup:kotlinpoet:1.12.0")
}
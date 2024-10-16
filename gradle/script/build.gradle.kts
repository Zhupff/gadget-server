import java.util.Properties

plugins {
    `kotlin-dsl`
}

private val gradleProperties = Properties().also { it.load(file("../../gradle.properties").inputStream()) }

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:${gradleProperties["kotlin.version"] as String}")
    compileOnly("org.jetbrains.compose:compose-gradle-plugin:${gradleProperties["compose.version"] as String}")
}

gradlePlugin {
    plugins {
        register("GadgetScript") {
            id = "gadget.script"
            implementationClass = "Script"
        }
    }
}
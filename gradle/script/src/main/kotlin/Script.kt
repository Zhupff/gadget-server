import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

class ApiScript : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("org.jetbrains.kotlin.jvm")
        target.pluginManager.apply("org.jetbrains.kotlin.kapt")

        target.repositories {
            google()
            mavenCentral()
            maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
            maven("https://jitpack.io")
        }

        target.extensions.getByType(JavaPluginExtension::class.java).run {
            toolchain.languageVersion.set(JavaLanguageVersion.of(17))
        }
        target.extensions.getByType(KotlinJvmProjectExtension::class.java).run {
            jvmToolchain(17)
        }
    }
}

class ClientScript : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("org.jetbrains.kotlin.jvm")
        target.pluginManager.apply("org.jetbrains.kotlin.kapt")
        target.pluginManager.apply("org.jetbrains.compose")

        target.group = "zhupff.gadget.client"
        target.version = "1.0-SNAPSHOT"

        target.repositories {
            google()
            mavenCentral()
            maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
            maven("https://jitpack.io")
        }

        target.extensions.getByType(JavaPluginExtension::class.java).run {
            toolchain.languageVersion.set(JavaLanguageVersion.of(17))
        }
        target.extensions.getByType(KotlinJvmProjectExtension::class.java).run {
            jvmToolchain(17)
        }

        target.dependencies {
            add(IMPLEMENTATION, ComposePlugin.Dependencies(target).desktop.currentOs)
        }
    }
}

class ServerScript : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("org.jetbrains.kotlin.jvm")
        target.pluginManager.apply("org.jetbrains.kotlin.kapt")
        target.pluginManager.apply("org.jetbrains.kotlin.plugin.spring")
        target.pluginManager.apply("io.spring.dependency-management")

        target.group = "zhupff.gadget.server"
        target.version = "1.0-SNAPSHOT"

        target.repositories {
            google()
            mavenCentral()
            maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
            maven("https://jitpack.io")
        }

        target.extensions.getByType(JavaPluginExtension::class.java).run {
            toolchain.languageVersion.set(JavaLanguageVersion.of(17))
        }
        target.extensions.getByType(KotlinJvmProjectExtension::class.java).run {
            jvmToolchain(17)
        }

        target.dependencies {
            add(IMPLEMENTATION, "org.springframework.boot:spring-boot-starter-web:3.3.1")
            add(IMPLEMENTATION, "com.fasterxml.jackson.module:jackson-module-kotlin:2.17.1")
            add(IMPLEMENTATION, "org.jetbrains.kotlin:kotlin-reflect:1.9.22")
        }
    }
}



private const val IMPLEMENTATION = "implementation"

fun DependencyHandlerScope.autoService(method: String = IMPLEMENTATION) {
    this.dependencies.add(method, "com.google.auto.service:auto-service-annotations:1.1.1")
    this.dependencies.add("kapt", "com.google.auto.service:auto-service:1.1.1")
}

fun DependencyHandlerScope.coroutines(method: String = IMPLEMENTATION) {
    this.dependencies.add(method, "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
}

fun DependencyHandlerScope.gson(method: String = IMPLEMENTATION) {
    this.dependencies.add(method, "com.google.code.gson:gson:2.11.0")
}

fun DependencyHandlerScope.javacv(method: String = IMPLEMENTATION) {
    this.dependencies.add(method, "org.bytedeco:javacv-platform:1.5.10")
}

fun DependencyHandlerScope.kotlinpoet(method: String = IMPLEMENTATION) {
    this.dependencies.add(method, "com.squareup:kotlinpoet:1.12.0")
}

fun DependencyHandlerScope.jcodec(method: String = IMPLEMENTATION) {
    this.dependencies.add(method, "org.jcodec:jcodec-javase:0.2.5")
}

fun DependencyHandlerScope.json(method: String = IMPLEMENTATION) {
    this.dependencies.add(method, "org.json:json:20240303")
}

fun DependencyHandlerScope.sqlite(method: String = IMPLEMENTATION) {
    this.dependencies.add(method, "org.springframework.boot:spring-boot-starter-jdbc:3.3.1")
    this.dependencies.add(method, "org.xerial:sqlite-jdbc:3.45.3.0")
}

fun DependencyHandlerScope.zxing(method: String = IMPLEMENTATION) {
    this.dependencies.add(method, "com.google.zxing:core:3.5.3")
}
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

class Script : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("org.jetbrains.kotlin.jvm")
        target.pluginManager.apply("org.jetbrains.kotlin.kapt")
        target.extensions.add(ScriptExtension::class.java, "script", ScriptExtension(target))
        target.extensions.getByType(JavaPluginExtension::class.java).run {
            toolchain.languageVersion.set(JavaLanguageVersion.of(17))
        }
        target.extensions.getByType(KotlinJvmProjectExtension::class.java).run {
            jvmToolchain(17)
        }
    }
}

class ScriptExtension(val project: Project) {

    fun client() {
        project.pluginManager.apply("org.jetbrains.compose")
        project.dependencies {
            add(IMPLEMENTATION, ComposePlugin.Dependencies(project).desktop.currentOs)
        }
    }

    fun server() {
        project.pluginManager.apply("org.jetbrains.kotlin.plugin.spring")
        project.pluginManager.apply("org.springframework.boot")
        project.pluginManager.apply("io.spring.dependency-management")
        project.dependencies {
            springboot(IMPLEMENTATION)
        }
    }

    fun publish() {
        project.pluginManager.apply("maven-publish")
        if (System.getenv("JITPACK").toBoolean()) {
            project.group = "${System.getenv("GROUP")}.${System.getenv("ARTIFACT")}"
            project.version = System.getenv("VERSION")
        } else {
            project.group = "zhupff.gadget.server"
            project.version = "0"
        }
        project.afterEvaluate {
            project.extensions.configure(PublishingExtension::class.java) {
                repositories {
                    mavenLocal()
                }
                publications {
                    create("MavenLocalPublication", MavenPublication::class.java) {
                        from(project.components.getByName("java"))
                        groupId = project.group.toString()
                        artifactId = project.name
                        version = project.version.toString()
                    }
                }
            }
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

fun DependencyHandlerScope.jcodec(method: String = IMPLEMENTATION) {
    this.dependencies.add(method, "org.jcodec:jcodec-javase:0.2.5")
}

fun DependencyHandlerScope.json(method: String = IMPLEMENTATION) {
    this.dependencies.add(method, "org.json:json:20240303")
}

fun DependencyHandlerScope.springboot(method: String = IMPLEMENTATION) {
    this.dependencies.add(method, "org.springframework.boot:spring-boot-starter-web")
    this.dependencies.add(method, "com.fasterxml.jackson.module:jackson-module-kotlin")
    this.dependencies.add(method, "org.jetbrains.kotlin:kotlin-reflect")
}

fun DependencyHandlerScope.zxing(method: String = IMPLEMENTATION) {
    this.dependencies.add(method, "com.google.zxing:core:3.5.3")
}
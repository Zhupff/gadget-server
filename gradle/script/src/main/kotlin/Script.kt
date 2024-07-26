import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.DependencyHandlerScope

class Script : Plugin<Project> {
    override fun apply(target: Project) {
        target.extensions.add(ScriptExtension::class.java, "script", ScriptExtension(target))
    }
}

class ScriptExtension(val project: Project) {

    fun publish() {
        project.pluginManager.apply("maven-publish")
        if (System.getenv("JITPACK").toBoolean()) {
            project.group = "${System.getenv("GROUP")}.${System.getenv("ARTIFACT")}"
            project.version = System.getenv("VERSION")
        } else {
            project.group = "zhupff.gadget"
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

fun DependencyHandlerScope.gson(method: String = IMPLEMENTATION) {
    this.dependencies.add(method, "com.google.code.gson:gson:2.11.0")
}

fun DependencyHandlerScope.zxing(method: String = IMPLEMENTATION) {
    this.dependencies.add(method, "com.google.zxing:core:3.5.3")
}
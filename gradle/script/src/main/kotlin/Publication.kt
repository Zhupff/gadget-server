import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication

class Publication : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("maven-publish")
        if (System.getenv("JITPACK").toBoolean()) {
            target.group = "${System.getenv("GROUP")}.${System.getenv("ARTIFACT")}"
            target.version = System.getenv("VERSION")
        } else {
            target.group = "zhupff.gadget"
            target.version = "0"
        }
        target.afterEvaluate {
            target.extensions.configure(PublishingExtension::class.java) {
                repositories {
                    mavenLocal()
                }
                publications {
                    create("MavenLocalPublication", MavenPublication::class.java) {
                        from(target.components.getByName("java"))
                        groupId = target.group.toString()
                        artifactId = target.name
                        version = target.version.toString()
                    }
                }
            }
        }
    }
}
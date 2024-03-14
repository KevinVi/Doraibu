import com.android.build.api.dsl.ApplicationExtension
import com.kevinvi.doraibu.plugins.configureAndroidCompose
import com.kevinvi.doraibu.plugins.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")

            val extension = extensions.getByType<ApplicationExtension>()
            configureAndroidCompose(extension)

            extension.apply {
                dependencies {
                    add("implementation", libs.findLibrary("androidx-compose-material3").get())
                }
            }
        }
    }
}
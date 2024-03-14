import com.android.build.api.dsl.ApplicationExtension
import com.kevinvi.doraibu.plugins.PluginSdkVersion
import com.kevinvi.doraibu.plugins.configureAndroidXLifecycle
import com.kevinvi.doraibu.plugins.configureAndroidXNavigation
import com.kevinvi.doraibu.plugins.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("jetpack.android.lint")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)

                defaultConfig.targetSdk = PluginSdkVersion.TARGET_SDK

                configureAndroidXLifecycle(this)
                configureAndroidXNavigation(this)
            }
        }
    }
}

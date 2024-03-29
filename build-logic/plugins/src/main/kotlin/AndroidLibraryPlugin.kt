import com.android.build.gradle.LibraryExtension
import com.kevinvi.doraibu.plugins.PluginSdkVersion
import com.kevinvi.doraibu.plugins.configureAndroidXNavigation
import com.kevinvi.doraibu.plugins.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = PluginSdkVersion.TARGET_SDK

                // The resource prefix is derived from the module name,
                // so resources inside ":core:module1" must be prefixed with "core_module1_"
                resourcePrefix = path
                    .split("""\W""".toRegex())
                    .drop(1)
                    .distinct()
                    .joinToString(separator = "_")
                    .lowercase() + "_"
                configureAndroidXNavigation(this)
            }
        }
    }
}
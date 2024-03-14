import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.kevinvi.doraibu.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "jetpack.android.application"
            implementationClass = "AndroidApplicationPlugin"
        }
        register("androidApplicationCompose") {
            id = "jetpack.android.application.compose"
            implementationClass = "AndroidApplicationComposePlugin"
        }
        register("androidFeature") {
            id = "jetpack.android.feature"
            implementationClass = "AndroidFeaturePlugin"
        }
        register("androidFeatureCompose") {
            id = "jetpack.android.feature.compose"
            implementationClass = "AndroidFeatureComposePlugin"
        }
        register("androidHilt") {
            id = "jetpack.android.hilt"
            implementationClass = "AndroidHiltPlugin"
        }
        register("androidLibrary") {
            id = "jetpack.android.library"
            implementationClass = "AndroidLibraryPlugin"
        }
        register("androidLibraryCompose") {
            id = "jetpack.android.library.compose"
            implementationClass = "AndroidLibraryComposePlugin"
        }
        register("androidLint") {
            id = "jetpack.android.lint"
            implementationClass = "AndroidLintPlugin"
        }
        register("androidXRoom") {
            id = "jetpack.androidx.room"
            implementationClass = "AndroidRoomPlugin"
        }
    }
}
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
    compileOnly(libs.firebase.crashlytics.gradlePlugin)
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
            id = "doraibu.android.application"
            implementationClass = "AndroidApplicationPlugin"
        }
        register("androidApplicationCompose") {
            id = "doraibu.android.application.compose"
            implementationClass = "AndroidApplicationComposePlugin"
        }
        register("androidApplicationFirebase") {
            id = "doraibu.android.application.firebase"
            implementationClass = "AndroidApplicationFirebasePlugin"
        }
        register("androidFeature") {
            id = "doraibu.android.feature"
            implementationClass = "AndroidFeaturePlugin"
        }
        register("androidFeatureCompose") {
            id = "doraibu.android.feature.compose"
            implementationClass = "AndroidFeatureComposePlugin"
        }
        register("androidHilt") {
            id = "doraibu.android.hilt"
            implementationClass = "AndroidHiltPlugin"
        }
        register("androidLibrary") {
            id = "doraibu.android.library"
            implementationClass = "AndroidLibraryPlugin"
        }
        register("androidLibraryCompose") {
            id = "doraibu.android.library.compose"
            implementationClass = "AndroidLibraryComposePlugin"
        }
        register("androidXRoom") {
            id = "doraibu.androidx.room"
            implementationClass = "AndroidRoomPlugin"
        }
    }
}
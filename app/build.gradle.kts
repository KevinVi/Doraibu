plugins {
	alias(libs.plugins.doraibu.android.application)
	alias(libs.plugins.doraibu.android.application.compose)
	alias(libs.plugins.doraibu.android.application.firebase)
	alias(libs.plugins.doraibu.android.hilt)

	id("kotlin-parcelize")

	kotlin("plugin.serialization") version libs.versions.kotlin.get()
}

android {
	namespace = "com.kevinvi.doraibu"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.kevinvi.doraibu"
		minSdk = 24
		targetSdk = 34
		versionCode = 2
		versionName = "1.2"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
	buildFeatures {
		buildConfig = true
	}
}

dependencies {

	implementation(libs.androidx.core.ktx)

	implementation(project(":core:common"))
	implementation(project(":core:data"))
	implementation(project(":core:ui"))

	implementation(project(":feature:scan"))
	implementation(project(":feature:anime"))
	implementation(project(":feature:tome"))

	// Coil
	implementation(libs.coil.compose)
	implementation(libs.coil.svg)


	// Ktor
	implementation(libs.bundles.ktor)


	// Worker
	implementation(libs.androidx.work.runtime.ktx)
	// Hilt Worker
	implementation(libs.androidx.hilt.work)
	ksp(libs.androidx.hilt.compiler)


	implementation(libs.androidx.navigation.fragment)
	implementation(libs.androidx.navigation.ui)

	// Accompanist permissions
	implementation(libs.com.google.accompanist.permissions)

	implementation(libs.androidx.datastore)
	implementation(libs.androidx.datastore.preferences)
}
plugins {
	alias(libs.plugins.doraibu.android.application)
	alias(libs.plugins.doraibu.android.application.compose)
	alias(libs.plugins.doraibu.android.hilt)
}

android {
	namespace = "com.kevinvi.doraibu"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.kevinvi.doraibu"
		minSdk = 24
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

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

	// Coil
	implementation(libs.coil.compose)
	implementation(libs.coil.svg)

	implementation(libs.androidx.navigation.fragment)
	implementation(libs.androidx.navigation.ui)
}
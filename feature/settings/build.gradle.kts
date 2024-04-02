plugins {
	alias(libs.plugins.doraibu.android.feature)
	alias(libs.plugins.doraibu.android.feature.compose)
	alias(libs.plugins.doraibu.android.hilt)
}

android {
	namespace = "com.kevinvi.doraibu.feature.settings"
	compileSdk = 34

	defaultConfig {
		minSdk = 24

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		consumerProguardFiles("consumer-rules.pro")
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
	kotlinOptions {
		jvmTarget = JavaVersion.VERSION_17.toString()
	}
}

dependencies {


	implementation(libs.androidx.core.ktx)


	// Coil
	implementation(libs.coil.compose)
	implementation(libs.coil.svg)


	// Lottie
	implementation(libs.lottie.loader)

	// Core
	implementation(project(":core:common"))
	implementation(project(":core:data"))
	implementation(project(":core:ui"))

}
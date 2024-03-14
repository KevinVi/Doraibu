plugins {
	alias(libs.plugins.doraibu.android.feature)
	alias(libs.plugins.doraibu.android.feature.compose)
	alias(libs.plugins.doraibu.android.hilt)
	alias(libs.plugins.doraibu.androidx.room)

	id("kotlin-parcelize")

}

android {
	namespace = "com.kevinvi.doraibu.feature.scan"
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
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	kotlinOptions {
		jvmTarget = "1.8"
	}
}

dependencies {


	implementation(libs.androidx.core.ktx)

	// Ktor
	implementation(libs.bundles.ktor)

	// Core
	implementation(project(":core:common"))
	implementation(project(":core:data"))
	implementation(project(":core:ui"))

}
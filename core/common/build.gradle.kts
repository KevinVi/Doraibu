plugins {
	alias(libs.plugins.doraibu.android.library)
	alias(libs.plugins.doraibu.android.library.compose)
	alias(libs.plugins.doraibu.android.hilt)
	alias(libs.plugins.doraibu.androidx.room)
	id("kotlin-parcelize")

	kotlin("plugin.serialization") version libs.versions.kotlin.get()
}

android {
	namespace = "com.kevinvi.common"
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

	// AndroidX Core
	implementation(libs.androidx.core.ktx)

	// Biometric

	// Ktor
	implementation(libs.bundles.ktor)

	// Browser

	// Navigation
	implementation(libs.androidx.navigation.compose)
}
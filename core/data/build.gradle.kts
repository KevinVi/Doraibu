plugins {
	alias(libs.plugins.doraibu.android.library)
	alias(libs.plugins.doraibu.android.hilt)
	alias(libs.plugins.doraibu.androidx.room)
	alias(libs.plugins.doraibu.android.feature)
	alias(libs.plugins.doraibu.android.feature.compose)

	id("kotlin-parcelize")

	kotlin("plugin.serialization") version libs.versions.kotlin.get()
}

android {
	namespace = "com.kevinvi.data"
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

	implementation(project(":core:common"))
	implementation(project(":core:ui"))
	implementation(libs.androidx.core.ktx)
	// Ktor
	implementation(libs.bundles.ktor)
}
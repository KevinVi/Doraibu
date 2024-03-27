plugins {
	alias(libs.plugins.doraibu.android.library)
	alias(libs.plugins.doraibu.android.library.compose)
}

android {
	namespace = "com.kevinvi.ui"
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
	// Material 3
	implementation(libs.androidx.compose.material3)

	// Lottie
	implementation(libs.lottie.loader)

}
plugins {
	alias(libs.plugins.android.application)
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
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
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
}
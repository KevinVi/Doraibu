pluginManagement {
	includeBuild("build-logic")
	repositories {
		google()
		mavenCentral()
		gradlePluginPortal()
		maven ("https://oss.sonatype.org/content/repositories/snapshots/" )

	}
}
dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		google()
		mavenCentral()
		maven ("https://oss.sonatype.org/content/repositories/snapshots/" )

	}
}

rootProject.name = "Doraibu"
include(":app")
include(":core")
include(":feature")
include(":feature:scan")
include(":core:ui")
include(":core:data")
include(":core:common")
include(":feature:anime")
include(":feature:tome")
include(":feature:settings")

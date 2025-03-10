pluginManagement {
  includeBuild("gradle/plugins")
}
plugins {
  id("com.gradle.develocity") version "3.19.2"
  id("com.gradle.common-custom-user-data-gradle-plugin") version "2.1"
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

dependencyResolutionManagement {
  repositories {
    maven { setUrl("https://maven.aliyun.com/repository/central") }
    maven { setUrl("https://maven.aliyun.com/repository/jcenter") }
    maven { setUrl("https://maven.aliyun.com/repository/google") }
    maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
    maven { setUrl("https://maven.aliyun.com/repository/public") }
    maven { setUrl("https://jitpack.io") }
    google()
    mavenCentral()
  }
}

apply(from = "$rootDir/gradle/develocity.gradle")

rootProject.name = "caffeine"
include("caffeine", "guava", "jcache", "simulator")

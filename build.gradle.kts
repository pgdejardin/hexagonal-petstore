import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.4.31"
  kotlin("kapt") version "1.4.31"
  base
  application
}

val http4kVersion: String by rootProject
val arrowVersion: String by rootProject
val kotestVersion: String by rootProject
val config4kVersion: String by rootProject
val logbackVersion: String by rootProject

allprojects {
  apply(plugin = "kotlin")
  apply(plugin = "kotlin-kapt")

  group = "org.example"
  version = "1.0-SNAPSHOT"

  repositories {
    mavenCentral()
    jcenter()
    maven { url = uri("https://dl.bintray.com/arrow-kt/arrow-kt/") }
  }

  tasks.withType<KotlinCompile> {
    kotlinOptions {
      jvmTarget = JavaVersion.VERSION_11.toString()
    }
  }

  tasks.withType<Test> {
    useJUnitPlatform()
  }

  dependencies {
    implementation(kotlin("stdlib"))

    implementation("io.arrow-kt:arrow-core:$arrowVersion")
    implementation("io.arrow-kt:arrow-syntax:$arrowVersion")
    kapt("io.arrow-kt:arrow-meta:$arrowVersion")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-arrow:$kotestVersion")
  }
}

dependencies {
  implementation(project(":infrastructure"))
  implementation(project(":domain"))

  implementation(platform("org.http4k:http4k-bom:$http4kVersion"))
  implementation("org.http4k:http4k-core")
  implementation("org.http4k:http4k-server-netty")
  implementation("org.http4k:http4k-client-apache")

  implementation("io.github.config4k:config4k:$config4kVersion")
}

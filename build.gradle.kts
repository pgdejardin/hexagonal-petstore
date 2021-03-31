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

allprojects {
  apply(plugin = "kotlin")
  apply(plugin = "kotlin-kapt")

  val compileKotlin: KotlinCompile by tasks
  compileKotlin.kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()

  group = "org.example"
  version = "1.0-SNAPSHOT"

  repositories {
    mavenCentral()
    jcenter()
    maven { url = uri("https://dl.bintray.com/arrow-kt/arrow-kt/") }
  }

  dependencies {
    implementation(kotlin("stdlib"))
//    implementation(group = "org.jetbrains.kotlinx", name= "kotlinx-coroutines-core", version= "1.0.0")

    implementation("io.arrow-kt:arrow-core:$arrowVersion")
    implementation("io.arrow-kt:arrow-syntax:$arrowVersion")
    kapt("io.arrow-kt:arrow-meta:$arrowVersion")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
  }
}

dependencies {
  implementation(kotlin("stdlib"))
  implementation(project(":infrastructure"))
  implementation(project(":domain"))

  implementation(platform("org.http4k:http4k-bom:$http4kVersion"))
  implementation("org.http4k:http4k-core")
  implementation("org.http4k:http4k-server-netty")
  implementation("org.http4k:http4k-client-apache")
}

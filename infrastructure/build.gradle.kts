val http4kVersion: String by rootProject
val config4kVersion: String by rootProject

dependencies {
  implementation(project(":domain"))

  implementation(platform("org.http4k:http4k-bom:$http4kVersion"))
  implementation("org.http4k:http4k-core")
  implementation("org.http4k:http4k-format-jackson")
  implementation("org.http4k:http4k-client-apache")

  implementation("io.github.config4k:config4k:$config4kVersion")

  implementation(platform("software.amazon.awssdk:bom:2.15.0"))
  implementation("software.amazon.awssdk:sqs")

  testImplementation("org.http4k:http4k-testing-kotest")
}

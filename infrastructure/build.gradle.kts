val http4kVersion: String by rootProject

dependencies {
  implementation(project(":domain"))

  implementation(platform("org.http4k:http4k-bom:$http4kVersion"))
  implementation("org.http4k:http4k-core")
  implementation("org.http4k:http4k-format-jackson")
  implementation("org.http4k:http4k-client-apache")
}

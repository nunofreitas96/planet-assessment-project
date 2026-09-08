plugins {
    id(Plugins.kotlinJvm) version Versions.kotlin
    id(Plugins.kotlinSpring) version Versions.kotlin
}

dependencies {
    implementation(project(":domain"))

    implementation(SpringBootDependencies.webFlux)
    implementation(SpringBootDependencies.dataJpa)
    testImplementation(TestDependencies.junitJupiter)

    implementation("commons-validator:commons-validator:1.10.0")
    implementation("com.googlecode.libphonenumber:libphonenumber:9.0.38")

}
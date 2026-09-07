plugins {
    id(Plugins.kotlinJvm) version Versions.kotlin
    id(Plugins.kotlinSpring) version Versions.kotlin
}

dependencies {
    implementation(project(":domain"))

    implementation(SpringBootDependencies.webFlux)
    implementation(SpringBootDependencies.dataJpa)
    testImplementation(TestDependencies.junitJupiter)

}
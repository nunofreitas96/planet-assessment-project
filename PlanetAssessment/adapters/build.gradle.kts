plugins {
    id(Plugins.springFrameworkBoot) version Versions.springFramework
    id(Plugins.springDependencyManagement) version Versions.springDependencyManagement
    id(Plugins.openApiGenerator) version "7.25.0"
    id(Plugins.kotlinSpring) version Versions.kotlin
    id(Plugins.kotlinJvm) version Versions.kotlin
}

dependencies {
    implementation(project(":application"))
    implementation(project(":domain"))
    implementation(SpringBootDependencies.actuator)
    implementation(SpringBootDependencies.openApiWebFlux) {
        exclude(group = "jakarta.validation", module = "jakarta.validation-api")
    }
    implementation(SpringBootDependencies.validation)
    implementation(SpringBootDependencies.webFlux)
    implementation(SpringBootDependencies.aop)

    testImplementation(TestDependencies.junitJupiter)
}
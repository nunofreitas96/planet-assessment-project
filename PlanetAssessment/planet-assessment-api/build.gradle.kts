plugins {
    id(Plugins.springFrameworkBoot) version Versions.springFramework
    id(Plugins.springDependencyManagement) version Versions.springDependencyManagement
    id(Plugins.openApiGenerator) version "7.25.0"
    id(Plugins.kotlinJvm) version Versions.kotlin
    id(Plugins.kotlinSpring) version Versions.kotlin
}

dependencies {

    implementation(project(":adapters"))
    implementation(project(":application"))


    implementation(SpringBootDependencies.webFlux)
    implementation(SpringBootDependencies.dataJpa)

    testImplementation(TestDependencies.junitJupiter)
}

springBoot {
    buildInfo()
}
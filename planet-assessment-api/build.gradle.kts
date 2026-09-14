plugins {
    id(Plugins.springFrameworkBoot) version Versions.springFramework
    id(Plugins.springDependencyManagement) version Versions.springDependencyManagement
    id(Plugins.openApiGenerator) version Versions.openApiGenerator
    id(Plugins.kotlinJvm) version Versions.kotlin
    id(Plugins.kotlinSpring) version Versions.kotlin
    id(Plugins.ktLint) version Versions.ktLint
}

dependencies {

    implementation(project(":adapters"))
    implementation(project(":application"))

    implementation(SpringBootDependencies.dataJpa)
}

springBoot {
    buildInfo()
}

tasks.test {
    useJUnitPlatform()
}

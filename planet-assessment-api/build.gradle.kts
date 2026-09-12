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


    implementation("org.springframework.boot:spring-boot-starter-web:4.1.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.1.1")
    implementation(SpringBootDependencies.dataJpa)
    runtimeOnly("org.postgresql:postgresql")

    testImplementation(TestDependencies.junitJupiter)
}

springBoot {
    buildInfo()
}
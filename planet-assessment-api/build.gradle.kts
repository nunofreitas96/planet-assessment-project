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

    // Testing
    testImplementation(TestDependencies.junitJupiter)
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation("org.mockito:mockito-inline:5.3.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test:4.1.0")
}

springBoot {
    buildInfo()
}

// Ensure JUnit Platform is used for running tests in this module
tasks.test {
    useJUnitPlatform()
}
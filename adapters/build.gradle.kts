import org.gradle.kotlin.dsl.compileClasspath
import org.gradle.kotlin.dsl.runtimeClasspath

plugins {
    id(Plugins.springFrameworkBoot) version Versions.springFramework
    id(Plugins.springDependencyManagement) version Versions.springDependencyManagement
    id(Plugins.openApiGenerator) version "7.25.0"
    id(Plugins.kotlinSpring) version Versions.kotlin
    id(Plugins.kotlinJvm) version Versions.kotlin
    kotlin("plugin.jpa")
}


sourceSets {
    main {
        java.srcDir(layout.buildDirectory.dir("generated/openapi/src/main/kotlin"))
    }
    // integration test source set (it)
    create("it") {
        java.srcDir("src/it/kotlin")
        resources.srcDir("src/it/resources")

        compileClasspath += sourceSets["main"].output
        runtimeClasspath += sourceSets["main"].output
    }
}
tasks.withType<ProcessResources> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}


tasks.named("compileKotlin") {
    dependsOn(tasks.named("openApiGenerate"))
}

tasks.test {
    useJUnitPlatform()
}

val itTest by tasks.registering(Test::class) {
    description = "Runs integration tests (src/it/kotlin)."
    group = "verification"
    testClassesDirs = sourceSets["it"].output.classesDirs
    classpath = sourceSets["it"].runtimeClasspath
    shouldRunAfter(tasks.test)
    useJUnitPlatform()
}

dependencies {
    implementation(project(":application"))
    implementation(project(":domain"))
    implementation("org.springframework.boot:spring-boot-starter-web:4.1.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.1.1")
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation(SpringBootDependencies.actuator)
    implementation(SpringBootDependencies.validation)
    implementation(SpringBootDependencies.aop)
    implementation(SpringBootDependencies.dataJpa)
    implementation("org.springframework:spring-context:7.0.9")
    testImplementation(kotlin("test"))
    testImplementation(TestDependencies.junitJupiter)


    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test:4.1.0")
    // Ensure Spring Boot application main is on test classpath to allow @SpringBootTest to find it
    testImplementation(project(":planet-assessment-api"))
    testImplementation("org.springframework.boot:spring-boot-test-autoconfigure:4.1.0")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test:4.1.0")
    implementation("org.springframework.boot:spring-boot-webtestclient:4.1.0")

    implementation("org.jetbrains.kotlin:kotlin-reflect:2.4.20")
    implementation("jakarta.servlet:jakarta.servlet-api:6.2.0-M2")
    implementation("org.apache.commons:commons-csv:1.14.1")
    testImplementation("org.testcontainers:testcontainers:2.0.5")
    testImplementation("org.testcontainers:junit-jupiter:1.21.4")
}

// ensure integration test configurations extend testImplementation/testRuntimeOnly
configurations["itImplementation"].extendsFrom(configurations["testImplementation"])
configurations["itRuntimeOnly"].extendsFrom(configurations["testRuntimeOnly"])

openApiGenerate {
    generatorName.set("kotlin-spring")
    inputSpec.set(layout.projectDirectory.file("src/main/resources/static/openapi.yaml"))
    outputDir.set(layout.buildDirectory.dir("generated/openapi"))
    apiPackage.set("com.planetassessment.adapters.api")
    modelPackage.set("com.planetassessment.adapters.model")
    invokerPackage.set("com.planetassessment.adapters.invoker")
    configOptions.set(
        mapOf(
            "dateLibrary" to "java8",
            "useSpringBoot3" to "true",
            "interfaceOnly" to "true",
            "useTags" to "true",
            "apiNameSuffix" to "Api"
        )
    )
}

// Disable bootJar for non-executable modules
tasks.named("bootJar") {
    enabled = false
}
repositories {
    mavenCentral()
}
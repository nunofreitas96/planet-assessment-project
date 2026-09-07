plugins {
    id(Plugins.springFrameworkBoot) version Versions.springFramework
    id(Plugins.springDependencyManagement) version Versions.springDependencyManagement
    id(Plugins.openApiGenerator) version "7.25.0"
    id(Plugins.kotlinSpring) version Versions.kotlin
    id(Plugins.kotlinJvm) version Versions.kotlin
}


sourceSets {
    main {
        java.srcDir(layout.buildDirectory.dir("generated/openapi/src/main/kotlin"))
    }
}

tasks.named("compileKotlin") {
    dependsOn(tasks.named("openApiGenerate"))
}

dependencies {
    implementation(project(":application"))
    implementation(project(":domain"))
    implementation(SpringBootDependencies.actuator)
    implementation(SpringBootDependencies.openApiWebFlux)
    implementation(SpringBootDependencies.validation)
    implementation(SpringBootDependencies.webFlux)
    implementation(SpringBootDependencies.aop)
    implementation(SpringBootDependencies.dataJpa)

    testImplementation(TestDependencies.junitJupiter)

    implementation("jakarta.servlet:jakarta.servlet-api:6.2.0-M2")
    implementation("org.apache.commons:commons-csv:1.14.1")
}

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

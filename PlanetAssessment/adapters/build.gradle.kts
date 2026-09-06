plugins {
    id(Plugins.springFrameworkBoot) version Versions.springFramework
    id(Plugins.springDependencyManagement) version Versions.springDependencyManagement
    id(Plugins.openApiGenerator) version "7.25.0"
    id(Plugins.kotlinSpring) version Versions.kotlin
    id(Plugins.kotlinJvm) version Versions.kotlin
}


sourceSets {
    main {
        java.srcDir("$buildDir/generated/openapi/src/main/kotlin")
    }
}

tasks.named("compileKotlin") {
    dependsOn(tasks.named("openApiGenerate"))
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


openApiGenerate {
    generatorName.set("kotlin-spring")
    inputSpec.set("$projectDir/src/main/resources/static/openapi.yaml")
    outputDir.set("$buildDir/generated/openapi")
    apiPackage.set("com.planetassessment.adapters.api")
    modelPackage.set("com.planetassessment.adapters.model")
    invokerPackage.set("com.planetassessment.adapters.invoker")
    configOptions.set(
        mapOf(
            "dateLibrary" to "java8",
            "useSpringBoot3" to "true",
            "interfaceOnly" to "true",
            "useTags" to "true",
            "skipDefaultInterface" to "false"
        )
    )
}
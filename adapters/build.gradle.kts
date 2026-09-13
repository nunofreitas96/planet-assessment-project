plugins {
    id(Plugins.springFrameworkBoot) version Versions.springFramework
    id(Plugins.springDependencyManagement) version Versions.springDependencyManagement
    id(Plugins.openApiGenerator) version Versions.openApiGenerator
    id(Plugins.kotlinSpring) version Versions.kotlin
    id(Plugins.kotlinJvm) version Versions.kotlin
    kotlin("plugin.jpa")
}


sourceSets {
    main {
        java.srcDir(layout.buildDirectory.dir("generated/openapi/src/main/kotlin"))
    }
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
    implementation(SpringBootDependencies.starterWeb)
    implementation(TestDependencies.openApiwebvmc)
    implementation(LoggerDependencies.slf4j)
    implementation(SpringBootDependencies.actuator)
    implementation(SpringBootDependencies.validation)
    implementation(SpringBootDependencies.aop)
    implementation(SpringBootDependencies.dataJpa)
    implementation(SpringBootDependencies.springContext)
    implementation(SpringBootDependencies.springWebTestClient)
    implementation(ObjectDependencies.kotlinReflect)
    implementation(ObjectDependencies.jakarta)
    implementation(ObjectDependencies.commonsCSV)

    testImplementation(kotlin("test"))
    testImplementation(TestDependencies.junitJupiter)
    testImplementation(TestDependencies.mockito)
    testImplementation(SpringBootDependencies.springStarterTest)
    testImplementation(project(":planet-assessment-api"))
    testImplementation(SpringBootDependencies.testAutoconfig)
    testImplementation(SpringBootDependencies.springWebMvc)
    testImplementation(TestDependencies.testContainers)
    testImplementation(TestDependencies.testContainersJupiters)

    runtimeOnly(PostgreSQLDependencies.postgresql)
}

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

tasks.named("bootJar") {
    enabled = false
}
repositories {
    mavenCentral()
}
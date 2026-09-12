plugins {
    kotlin("jvm") version "2.4.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    gradlePluginPortal()

    maven {
        name = "Confluent"
        url = uri("https://packages.confluent.io/maven/")
        isAllowInsecureProtocol = true
    }
    maven{
        url = uri("https://plugins.gradle.org/m2/")
    }
}

dependencies {

    implementation("org.slf4j:slf4j-api:2.0.17")
    testImplementation(kotlin("test"))
}

allprojects{
    repositories {
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()

        maven {
            name = "Confluent"
            url = uri("https://packages.confluent.io/maven/")
            isAllowInsecureProtocol = true
        }
        maven{
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
}

subprojects {
    plugins.withType<org.gradle.api.plugins.JavaPlugin> {
        java {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(17))
            }
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
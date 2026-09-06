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

tasks.test {
    useJUnitPlatform()
}
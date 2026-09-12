plugins {
    id(Plugins.kotlinJvm) version Versions.kotlin
    id(Plugins.kotlinSpring) version Versions.kotlin
}

dependencies {
    implementation(project(":domain"))


    implementation("org.springframework.boot:spring-boot-starter-web:4.1.0")
    implementation(SpringBootDependencies.dataJpa)
    testImplementation(TestDependencies.junitJupiter)

    implementation("org.slf4j:slf4j-api:2.0.17")

    implementation("commons-validator:commons-validator:1.10.0")
    implementation("com.googlecode.libphonenumber:libphonenumber:9.0.38")


    implementation("org.jetbrains.kotlin:kotlin-reflect:2.4.20")
    implementation("org.apache.commons:commons-csv:1.14.1")
    implementation("org.apache.poi:poi:5.5.1")
    implementation("org.apache.poi:poi-ooxml:5.5.1")

}
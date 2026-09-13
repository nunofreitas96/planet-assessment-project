plugins {
    id(Plugins.kotlinJvm) version Versions.kotlin
    id(Plugins.kotlinSpring) version Versions.kotlin
}


tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation(project(":domain"))


    implementation(SpringBootDependencies.starterWeb)
    implementation(SpringBootDependencies.dataJpa)
    implementation(LoggerDependencies.slf4j)
    implementation(ObjectDependencies.commonsValidator)
    implementation(ObjectDependencies.libPhoneNumber)
    implementation(ObjectDependencies.kotlinReflect)
    implementation(ObjectDependencies.commonsCSV)
    implementation(ApachePOI.apacheJoi)
    implementation(ApachePOI.apacheJoiOOXML)

    testImplementation(kotlin("test"))
    testImplementation(TestDependencies.junitJupiter)
    testImplementation(TestDependencies.mockito)
    testImplementation(SpringBootDependencies.springStarterTest)

}
object SpringBootDependencies {
    const val aop = "org.springframework.boot:spring-boot-starter-aop:${Versions.springAop}"
    const val actuator = "org.springframework.boot:spring-boot-starter-actuator:${Versions.springFramework}"

    const val validation = "org.springframework.boot:spring-boot-starter-validation:${Versions.springValidation}"
    const val aspects = "org.springframework:spring-aspects:${Versions.springAspects}"
    const val dataJpa = "org.springframework.boot:spring-boot-starter-data-jpa:${Versions.springFramework}"
    const val starterWeb = "org.springframework.boot:spring-boot-starter-web:${Versions.springFramework}"
    const val springContext = "org.springframework:spring-context:${Versions.springContext}"
    const val springStarterTest = "org.springframework.boot:spring-boot-starter-test:${Versions.springFramework}"
    const val testAutoconfig = "org.springframework.boot:spring-boot-test-autoconfigure:${Versions.springFramework}"
    const val springWebMvc = "org.springframework.boot:spring-boot-starter-webmvc-test:${Versions.springFramework}"
    const val springWebTestClient = "org.springframework.boot:spring-boot-webtestclient:${Versions.springFramework}"
}

object TestDependencies {
    const val junitJupiter = "org.junit.jupiter:junit-jupiter:${Versions.junitJupiter}"
    const val mockito = "org.mockito.kotlin:mockito-kotlin:${Versions.mockito}"
    const val testContainers = "org.testcontainers:testcontainers:${Versions.testContainers}"
    const val testContainersJupiters = "org.testcontainers:junit-jupiter:${Versions.testContainersJupiter}"
    const val openApiwebvmc = "org.springdoc:springdoc-openapi-starter-webmvc-ui:${Versions.opeanApiWebVmc}"
}

object ObjectDependencies {
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlinReflect}"
    const val jakarta  = "jakarta.servlet:jakarta.servlet-api:${Versions.jakarta}"
    const val commonsCSV = "org.apache.commons:commons-csv:${Versions.commonsCSV}"
    const val commonsValidator = "commons-validator:commons-validator:${Versions.commonsValidator}"
    const val libPhoneNumber = "com.googlecode.libphonenumber:libphonenumber:${Versions.libPhoneNumber}"
}

object ApachePOI {
    const val apacheJoi = "org.apache.poi:poi:${Versions.apachePOI}"
    const val apacheJoiOOXML = "org.apache.poi:poi-ooxml:${Versions.apachePOI}"
}

object LoggerDependencies {
    const val slf4j = "org.slf4j:slf4j-api:${Versions.slf4j}"
}

object PostgreSQLDependencies {
    const val postgresql = "org.postgresql:postgresql"
}
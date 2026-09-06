object SpringBootDependencies {
    const val aop = "org.springframework.boot:spring-boot-starter-aop:${Versions.springFramework}"
    const val actuator = "org.springframework.boot:spring-boot-starter-actuator:${Versions.springFramework}"
    const val openApiWebFlux =
        "org.springdoc:springdoc-openapi-starter-webflux-ui:${Versions.springDocOpenApiForWebFlux}"
    const val validation = "org.springframework.boot:spring-boot-starter-validation:${Versions.springValidation}"
    const val webFlux = "org.springframework.boot:spring-boot-starter-webflux:${Versions.springFramework}"
    const val aspects = "org.springframework:spring-aspects:${Versions.springAspects}"
    const val dataJpa = "org.springframework.boot:spring-boot-starter-data-jpa:${Versions.springFramework}"
}

object TestDependencies {
    const val junitJupiter = "org.junit.jupiter:junit-jupiter:${Versions.junitJupiter}"
}
object SpringBootDependencies {
    const val aop = "org.springframework.boot:spring-boot-starter-aop:4.0.0-M1"
    const val actuator = "org.springframework.boot:spring-boot-starter-actuator:${Versions.springFramework}"

    const val validation = "org.springframework.boot:spring-boot-starter-validation:${Versions.springValidation}"
    const val aspects = "org.springframework:spring-aspects:${Versions.springAspects}"
    const val dataJpa = "org.springframework.boot:spring-boot-starter-data-jpa:${Versions.springFramework}"
}

object TestDependencies {
    const val junitJupiter = "org.junit.jupiter:junit-jupiter:${Versions.junitJupiter}"
}
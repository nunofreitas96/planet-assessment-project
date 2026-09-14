package com.planet.assessment

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories("com.planet.assessment")
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

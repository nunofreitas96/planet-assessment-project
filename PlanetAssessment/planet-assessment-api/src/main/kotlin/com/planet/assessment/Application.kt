package com.planet.assessment

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@ComponentScan("com.planet.assessment")
@EntityScan("com.planet.assessment")
@EnableJpaRepositories("com.planet.assessment")
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
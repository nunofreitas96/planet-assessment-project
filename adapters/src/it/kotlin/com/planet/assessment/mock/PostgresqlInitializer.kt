package com.planet.assessment.mock

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.stereotype.Component
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container

@Component
class PostgresqlInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    companion object {
        class KGenericContainer(imageName: String) : GenericContainer<KGenericContainer>(imageName)

        private const val POSTGRES_PORT = 5432
        private const val POSTGRES_USR = "postgres"
        private const val POSTGRES_PWD = "postgres"
        private const val POSTGRES_DATABASE = "postgres"

        @Container
        var postgresql: KGenericContainer =
            KGenericContainer("postgres:15.2-alpine")
                .withEnv("POSTGRES_USER", POSTGRES_USR)
                .withEnv("POSTGRES_PASSWORD", POSTGRES_PWD)
                .withEnv("POSTGRES_DB", POSTGRES_DATABASE)
                .withExposedPorts(POSTGRES_PORT)
    }

    private val logger: Logger = LoggerFactory.getLogger(PostgresqlInitializer::class.java)

    override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
        postgresql.start()
        logger.debug("Initializing postgresql running...")
        TestPropertyValues.of(
            mapOf(
                "postgres.host" to "localhost",
                "postgres.port" to "${postgresql.firstMappedPort}",
                "postgres.username" to POSTGRES_USR,
                "postgres.password" to POSTGRES_PWD,
                "postgres.database" to POSTGRES_DATABASE,
            ),
        ).applyTo(configurableApplicationContext)
    }
}
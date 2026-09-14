package com.planet.assessment

import com.planet.assessment.mock.PostgresqlInitializer
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration(
    initializers = [
        PostgresqlInitializer::class,
    ],
)
abstract class BaseIntegrationTest

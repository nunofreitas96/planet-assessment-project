package com.planet.assessment.application.validator

import com.planet.assessment.user.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class EmailValidatorTest {

    private val validator = EmailValidator()

    @ParameterizedTest
    @CsvSource(
        "test@example.com, true",
        "invalid-email, false",
        " , false"
    )
    fun `validate various email values`(email: String?, expected: Boolean) {
        val normalized = email ?: ""
        val user = User(id = 1L, email = normalized)
        assertEquals(expected, validator.validate(user))
    }
}

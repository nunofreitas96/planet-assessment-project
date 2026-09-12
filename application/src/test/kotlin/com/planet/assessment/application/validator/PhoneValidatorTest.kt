package com.planet.assessment.application.validator

import com.planet.assessment.user.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class PhoneValidatorTest {

    private val validator = PhoneValidator()

    @ParameterizedTest
    @CsvSource(
        "912345678, true",
        "123, false",
        " , false"
    )
    fun `validate various phone values`(phone: String?, expected: Boolean) {
        val normalized = phone ?: ""
        val user = User(id = 1L, phone = normalized)
        assertEquals(expected, validator.validate(user))
    }
}

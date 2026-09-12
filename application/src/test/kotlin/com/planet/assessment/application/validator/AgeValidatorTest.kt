package com.planet.assessment.application.validator

import com.planet.assessment.user.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class AgeValidatorTest {

    private val validator = AgeValidator()

    @Test
    fun `shouldValidate returns false when age is null`() {
        val user = User(id = 1L)
        assertFalse(validator.shouldValidate(user))
    }

    @ParameterizedTest
    @CsvSource(
        "25, true",
        ", false",
        "abc, false"
    )
    fun `validate various ages`(age: String?, expected: Boolean) {
        val normalized = age ?: ""
        val user = User(id = 1L, age = normalized)
        assertEquals(expected, validator.validate(user))
    }
}

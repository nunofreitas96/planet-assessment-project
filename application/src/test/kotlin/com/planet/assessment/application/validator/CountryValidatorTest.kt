package com.planet.assessment.application.validator

import com.planet.assessment.user.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class CountryValidatorTest {

    private val validator = CountryValidator()

    @Test
    fun `shouldValidate returns false when country is null`() {
        val user = User(id = 1L)
        assertFalse(validator.shouldValidate(user))
    }

    @ParameterizedTest
    @ValueSource(strings = ["PT", "US"]) 
    fun `validate non blank countries`(country: String) {
        val user = User(id = 1L, country = country)
        assertTrue(validator.validate(user))
    }

    @Test
    fun `validate blank country returns false`() {
        val user = User(id = 1L, country = "")
        assertFalse(validator.validate(user))
    }
}

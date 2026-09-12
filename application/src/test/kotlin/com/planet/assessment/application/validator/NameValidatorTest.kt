package com.planet.assessment.application.validator

import com.planet.assessment.user.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class NameValidatorTest {

    private val validator = NameValidator()

    @Test
    fun `shouldValidate returns false when name is null`() {
        val user = User(id = 1L)
        assertFalse(validator.shouldValidate(user))
    }

    @Test
    fun `validate non blank name`() {
        val user = User(id = 1L, name = "Nuno")
        assertTrue(validator.validate(user))
    }

    @Test
    fun `validate blank name returns false`() {
        val user = User(id = 1L, name = "")
        assertFalse(validator.validate(user))
    }
}

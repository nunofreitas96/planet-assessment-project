package com.planet.assessment.application.validator

import com.planet.assessment.user.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

class AgeValidatorTest {

    private val validator = AgeValidator()

    @Test
    fun `shouldValidate returns false when age is null`() {
        val user = User(id = 1L)
        assertFalse(validator.shouldValidate(user))
    }

    companion object {
        @JvmStatic
        fun provideAges(): Stream<Arguments> = Stream.of(
            Arguments.of("25", true),
            Arguments.of("", false),
            Arguments.of("abc", false)
        )
    }

    @ParameterizedTest
    @MethodSource("provideAges")
    fun `validate various ages`(age: String, expected: Boolean) {
        val user = User(id = 1L, age = age)
        assertEquals(expected, validator.validate(user))
    }
}

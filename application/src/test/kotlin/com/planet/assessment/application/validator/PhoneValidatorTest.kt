package com.planet.assessment.application.validator

import com.planet.assessment.user.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

class PhoneValidatorTest {

    private val validator = PhoneValidator()

    companion object {
        @JvmStatic
        fun providePhones(): Stream<Arguments> = Stream.of(
            Arguments.of("912345678", true),
            Arguments.of("123", false),
            Arguments.of("", false)
        )
    }

    @ParameterizedTest
    @MethodSource("providePhones")
    fun `validate various phone values`(phone: String, expected: Boolean) {
        val user = User(id = 1L, phone = phone)
        assertEquals(expected, validator.validate(user))
    }
}

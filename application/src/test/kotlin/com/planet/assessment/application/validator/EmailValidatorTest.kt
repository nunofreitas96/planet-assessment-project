package com.planet.assessment.application.validator

import com.planet.assessment.user.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

class EmailValidatorTest {

    private val validator = EmailValidator()

    companion object {
        @JvmStatic
        fun provideEmails(): Stream<Arguments> = Stream.of(
            Arguments.of("test@example.com", true),
            Arguments.of("invalid-email", false),
            Arguments.of("", false)
        )
    }

    @ParameterizedTest
    @MethodSource("provideEmails")
    fun `validate various email values`(email: String, expected: Boolean) {
        val user = User(id = 1L, email = email)
        assertEquals(expected, validator.validate(user))
    }
}

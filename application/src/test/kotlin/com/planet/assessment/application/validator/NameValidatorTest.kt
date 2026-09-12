package com.planet.assessment.application.validator

import com.planet.assessment.user.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

class NameValidatorTest {

    private val validator = NameValidator()

    companion object {
        @JvmStatic
        fun provideNames(): Stream<Arguments> = Stream.of(
            Arguments.of(null as String?, false, false),
            Arguments.of("", true, false),
            Arguments.of("Nuno", true, true)
        )
    }

    @ParameterizedTest
    @MethodSource("provideNames")
    fun `name validation scenarios`(name: String?, shouldValidateExpected: Boolean, validateExpected: Boolean) {
        val user = User(id = 1L, name = name)
        assertEquals(shouldValidateExpected, validator.shouldValidate(user))
        if (shouldValidateExpected) {
            assertEquals(validateExpected, validator.validate(user))
        }
    }
}

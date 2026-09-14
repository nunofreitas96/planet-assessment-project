package com.planet.assessment.application.validator

import com.planet.assessment.application.TestUtils.DEFAULT_AGE
import com.planet.assessment.application.TestUtils.buildUser
import com.planet.assessment.application.TestUtils.buildValidationResult
import com.planet.assessment.user.UserDiscardReason
import com.planet.assessment.user.UserValidationResult
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class AgeValidatorTest {
    private val validator = AgeValidator()

    companion object {
        @JvmStatic
        fun provideAges(): Stream<Arguments> =
            Stream.of(
                Arguments.of(
                    DEFAULT_AGE,
                    buildValidationResult(isValid = true),
                    true,
                ),
                Arguments.of(
                    "",
                    buildValidationResult(isValid = false, discardReason = UserDiscardReason.BLANK_AGE),
                    true,
                ),
                Arguments.of(
                    "abc",
                    buildValidationResult(isValid = false, discardReason = UserDiscardReason.INVALID_AGE),
                    true,
                ),
                Arguments.of(
                    null,
                    buildValidationResult(isValid = false, discardReason = UserDiscardReason.INVALID_AGE),
                    false,
                ),
            )
    }

    @ParameterizedTest
    @MethodSource("provideAges")
    fun `validate various ages`(
        age: String?,
        expected: UserValidationResult,
        shouldValidateExpected: Boolean,
    ) {
        val user = buildUser(id = 1L, age = age)
        assertEquals(shouldValidateExpected, validator.shouldValidate(user))
        if (shouldValidateExpected) {
            assertEquals(expected, validator.validate(user))
        }
    }
}

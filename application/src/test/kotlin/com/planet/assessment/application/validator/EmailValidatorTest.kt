package com.planet.assessment.application.validator

import com.planet.assessment.application.TestUtils.DEFAULT_EMAIL
import com.planet.assessment.application.TestUtils.buildUser
import com.planet.assessment.application.TestUtils.buildValidationResult
import com.planet.assessment.user.UserDiscardReason
import com.planet.assessment.user.UserValidationResult
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class EmailValidatorTest {
    private val validator = EmailValidator()

    companion object {
        @JvmStatic
        fun provideEmails(): Stream<Arguments> =
            Stream.of(
                Arguments.of(
                    DEFAULT_EMAIL,
                    buildValidationResult(isValid = true),
                    true,
                ),
                Arguments.of(
                    "invalid-email",
                    buildValidationResult(isValid = false, discardReason = UserDiscardReason.INVALID_EMAIL),
                    true,
                ),
                Arguments.of(
                    "",
                    buildValidationResult(isValid = false, discardReason = UserDiscardReason.BLANK_EMAIL),
                    true,
                ),
                Arguments.of(
                    null,
                    buildValidationResult(isValid = false, discardReason = UserDiscardReason.INVALID_EMAIL),
                    false,
                ),
            )
    }

    @ParameterizedTest
    @MethodSource("provideEmails")
    fun `validate various email values`(
        email: String?,
        expected: UserValidationResult,
        shouldValidateExpected: Boolean,
    ) {
        val user = buildUser(id = 1L, email = email)
        validator.shouldValidate(user)
        if (shouldValidateExpected) {
            assertEquals(expected, validator.validate(user))
        }
    }
}

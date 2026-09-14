package com.planet.assessment.application.validator

import com.planet.assessment.application.TestUtils.DEFAULT_PHONE
import com.planet.assessment.application.TestUtils.DEFAULT_PHONE_WITH_IDENTIFIER
import com.planet.assessment.application.TestUtils.buildUser
import com.planet.assessment.application.TestUtils.buildValidationResult
import com.planet.assessment.user.UserDiscardReason
import com.planet.assessment.user.UserValidationResult
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class PhoneValidatorTest {
    private val validator = PhoneValidator()

    companion object {
        @JvmStatic
        fun providePhones(): Stream<Arguments> =
            Stream.of(
                Arguments.of(
                    DEFAULT_PHONE,
                    buildValidationResult(isValid = true),
                    true,
                ),
                Arguments.of(
                    DEFAULT_PHONE_WITH_IDENTIFIER,
                    buildValidationResult(isValid = true),
                    true,
                ),
                Arguments.of(
                    "123",
                    buildValidationResult(isValid = false, discardReason = UserDiscardReason.INVALID_PHONE),
                    true,
                ),
                Arguments.of(
                    "",
                    buildValidationResult(isValid = false, discardReason = UserDiscardReason.BLANK_PHONE),
                    true,
                ),
                Arguments.of(
                    null,
                    buildValidationResult(isValid = false, discardReason = UserDiscardReason.BLANK_PHONE),
                    false,
                ),
            )
    }

    @ParameterizedTest
    @MethodSource("providePhones")
    fun `validate various phone values`(
        phone: String?,
        expected: UserValidationResult,
        shouldValidateExpected: Boolean,
    ) {
        val user = buildUser(id = 1L, phone = phone)
        assertEquals(shouldValidateExpected, validator.shouldValidate(user))
        if (shouldValidateExpected) {
            assertEquals(expected, validator.validate(user))
        }
    }
}

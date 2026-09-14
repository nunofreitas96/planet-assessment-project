package com.planet.assessment.application.validator

import com.planet.assessment.application.TestUtils.DEFAULT_COUNTRY
import com.planet.assessment.application.TestUtils.buildUser
import com.planet.assessment.application.TestUtils.buildValidationResult
import com.planet.assessment.user.UserDiscardReason
import com.planet.assessment.user.UserValidationResult
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class CountryValidatorTest {
    private val validator = CountryValidator()

    companion object {
        @JvmStatic
        fun provideCountries(): Stream<Arguments> =
            Stream.of(
                Arguments.of(
                    null as String?,
                    false,
                    buildValidationResult(isValid = false, discardReason = UserDiscardReason.BLANK_COUNTRY),
                ),
                Arguments.of(
                    "",
                    true,
                    buildValidationResult(isValid = false, discardReason = UserDiscardReason.BLANK_COUNTRY),
                ),
                Arguments.of(
                    DEFAULT_COUNTRY,
                    true,
                    buildValidationResult(isValid = true),
                ),
            )
    }

    @ParameterizedTest
    @MethodSource("provideCountries")
    fun `country validation scenarios`(
        country: String?,
        shouldValidateExpected: Boolean,
        expected: UserValidationResult,
    ) {
        val user = buildUser(id = 1L, country = country)
        assertEquals(shouldValidateExpected, validator.shouldValidate(user))
        if (shouldValidateExpected) {
            assertEquals(expected, validator.validate(user))
        }
    }
}

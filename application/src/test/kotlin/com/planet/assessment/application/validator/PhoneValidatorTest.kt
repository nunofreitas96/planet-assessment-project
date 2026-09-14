package com.planet.assessment.application.validator

import com.planet.assessment.application.TestUtils.DEFAULT_PHONE
import com.planet.assessment.application.TestUtils.DEFAULT_PHONE_WITH_IDENTIFIER
import com.planet.assessment.application.TestUtils.buildUser
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
                Arguments.of(DEFAULT_PHONE, true, true),
                Arguments.of(DEFAULT_PHONE_WITH_IDENTIFIER, true, true),
                Arguments.of("123", false, true),
                Arguments.of("", false, true),
                Arguments.of(null, false, false),
            )
    }

    @ParameterizedTest
    @MethodSource("providePhones")
    fun `validate various phone values`(
        phone: String?,
        expected: Boolean,
        shouldValidateExpected: Boolean,
    ) {
        val user = buildUser(id = 1L, phone = phone)
        assertEquals(shouldValidateExpected, validator.shouldValidate(user))
        if (shouldValidateExpected) {
            assertEquals(expected, validator.validate(user))
        }
    }
}

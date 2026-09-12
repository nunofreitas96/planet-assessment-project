package com.planet.assessment.application.validator

import com.planet.assessment.user.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

class CountryValidatorTest {

    private val validator = CountryValidator()

    companion object {
        @JvmStatic
        fun provideCountries(): Stream<Arguments> = Stream.of(
            Arguments.of(null as String?, false, false),
            Arguments.of("", true, false),
            Arguments.of("PT", true, true),
            Arguments.of("US", true, true)
        )
    }

    @ParameterizedTest
    @MethodSource("provideCountries")
    fun `country validation scenarios`(country: String?, shouldValidateExpected: Boolean, validateExpected: Boolean) {
        val user = User(id = 1L, country = country)
        assertEquals(shouldValidateExpected, validator.shouldValidate(user))
        if (shouldValidateExpected) {
            assertEquals(validateExpected, validator.validate(user))
        }
    }
}

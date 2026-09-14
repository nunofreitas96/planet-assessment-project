package com.planet.assessment.application.validator

import com.planet.assessment.application.TestUtils.DEFAULT_NAME
import com.planet.assessment.application.TestUtils.buildUser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class NameValidatorTest {
    private val validator = NameValidator()

    companion object {
        @JvmStatic
        fun provideNames(): Stream<Arguments> =
            Stream.of(
                Arguments.of(null as String?, false, false),
                Arguments.of("", true, false),
                Arguments.of(DEFAULT_NAME, true, true),
            )
    }

    @ParameterizedTest
    @MethodSource("provideNames")
    fun `name validation scenarios`(
        name: String?,
        shouldValidateExpected: Boolean,
        validateExpected: Boolean,
    ) {
        val user = buildUser(id = 1L, name = name)
        assertEquals(shouldValidateExpected, validator.shouldValidate(user))
        if (shouldValidateExpected) {
            assertEquals(validateExpected, validator.validate(user))
        }
    }
}

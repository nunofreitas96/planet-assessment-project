package com.planet.assessment.application.mapper

import com.planet.assessment.application.TestUtils.DEFAULT_AGE
import com.planet.assessment.application.TestUtils.DEFAULT_COUNTRY
import com.planet.assessment.application.TestUtils.DEFAULT_EMAIL
import com.planet.assessment.application.TestUtils.DEFAULT_NAME
import com.planet.assessment.application.TestUtils.DEFAULT_PHONE
import com.planet.assessment.application.TestUtils.buildUser
import com.planet.assessment.application.mapper.UserValueMapper.valueOf
import com.planet.assessment.column.ExportColumn
import com.planet.assessment.user.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@DisplayName("UserValueMapper tests")
class UserValueMapperTest {
    companion object {
        @JvmStatic
        fun provideValues(): Stream<Arguments> =
            Stream.of(
                Arguments.of(
                    ExportColumn.ID,
                    42L,
                    buildUser(
                        id = 42L,
                        name = DEFAULT_NAME,
                        email = DEFAULT_EMAIL,
                        age = DEFAULT_AGE,
                        country = DEFAULT_COUNTRY,
                        phone = DEFAULT_PHONE,
                    ),
                ),
                Arguments.of(
                    ExportColumn.NAME,
                    DEFAULT_NAME,
                    buildUser(id = 1L, name = DEFAULT_NAME),
                ),
                Arguments.of(
                    ExportColumn.EMAIL,
                    DEFAULT_EMAIL,
                    buildUser(id = 1L, email = DEFAULT_EMAIL),
                ),
                Arguments.of(
                    ExportColumn.AGE,
                    DEFAULT_AGE,
                    buildUser(id = 1L, age = DEFAULT_AGE),
                ),
                Arguments.of(
                    ExportColumn.COUNTRY,
                    DEFAULT_COUNTRY,
                    buildUser(id = 1L, country = DEFAULT_COUNTRY),
                ),
                Arguments.of(
                    ExportColumn.PHONE,
                    DEFAULT_PHONE,
                    buildUser(id = 1L, phone = DEFAULT_PHONE),
                ),
            )
    }

    @ParameterizedTest
    @MethodSource("provideValues")
    fun `map user values to columns`(
        column: ExportColumn,
        expected: Any?,
        user: User,
    ) {
        val value = user.valueOf(column)
        assertEquals(expected, value)
    }
}

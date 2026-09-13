package com.planet.assessment.application.mapper

import com.planet.assessment.application.mapper.UserValueMapper.valueOf
import com.planet.assessment.column.ExportColumn
import com.planet.assessment.user.User
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.api.Assertions.assertEquals
import java.util.stream.Stream

@DisplayName("UserValueMapper tests")
class UserValueMapperTest {

    companion object {
        @JvmStatic
        fun provideValues(): Stream<Arguments> = Stream.of(
            Arguments.of(ExportColumn.ID, 42L, User(id = 42L, name = "x", email = "e", age = "1", country = "PT", phone = "9")),
            Arguments.of(ExportColumn.NAME, "John", User(id = 1L, name = "John")),
            Arguments.of(ExportColumn.EMAIL, "a@b.com", User(id = 1L, email = "a@b.com")),
            Arguments.of(ExportColumn.AGE, "30", User(id = 1L, age = "30")),
            Arguments.of(ExportColumn.COUNTRY, "PT", User(id = 1L, country = "PT")),
            Arguments.of(ExportColumn.PHONE, "912345678", User(id = 1L, phone = "912345678"))
        )
    }

    @ParameterizedTest
    @MethodSource("provideValues")
    fun `map user values to columns`(column: ExportColumn, expected: Any?, user: User) {
        val value = user.valueOf(column)
        assertEquals(expected, value)
    }
}

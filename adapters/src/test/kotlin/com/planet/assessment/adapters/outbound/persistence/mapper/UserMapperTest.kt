package com.planet.assessment.adapters.outbound.persistence.mapper

import com.planet.assessment.adapters.TestUtils.BLANK_COLUMN
import com.planet.assessment.adapters.TestUtils.DEFAULT_AGE
import com.planet.assessment.adapters.TestUtils.DEFAULT_AGE_COLUMN
import com.planet.assessment.adapters.TestUtils.DEFAULT_COUNTRY
import com.planet.assessment.adapters.TestUtils.DEFAULT_EMAIL
import com.planet.assessment.adapters.TestUtils.DEFAULT_NAME
import com.planet.assessment.adapters.TestUtils.DEFAULT_PHONE
import com.planet.assessment.adapters.TestUtils.WRONG_AGE
import com.planet.assessment.adapters.outbound.persistence.entity.UserEntity
import com.planet.assessment.adapters.outbound.persistence.mapper.UserMapper.toEntity
import com.planet.assessment.adapters.outbound.persistence.mapper.UserMapper.toInternalModel
import com.planet.assessment.user.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class UserMapperTest {
    companion object {
        @JvmStatic
        fun provideUsersForToEntity(): Stream<Arguments> =
            Stream.of(
                Arguments.of(
                    User(
                        id = 1L,
                        name = DEFAULT_NAME,
                        email = DEFAULT_EMAIL,
                        age = DEFAULT_AGE_COLUMN,
                        country = DEFAULT_COUNTRY,
                        phone = DEFAULT_PHONE,
                    ),
                    DEFAULT_AGE,
                ),
                Arguments.of(User(id = 2L, name = null, email = null, age = null, country = null, phone = null), null),
                Arguments.of(User(id = 3L, age = WRONG_AGE), null),
                Arguments.of(User(id = 4L, age = BLANK_COLUMN), null),
            )

        @JvmStatic
        fun provideEntitiesForToInternalModel(): Stream<Arguments> =
            Stream.of(
                Arguments.of(
                    UserEntity(
                        id = 1L,
                        name = DEFAULT_NAME,
                        email = DEFAULT_EMAIL,
                        age = DEFAULT_AGE,
                        country = DEFAULT_COUNTRY,
                        phone = DEFAULT_PHONE,
                    ),
                    DEFAULT_AGE_COLUMN,
                ),
                Arguments.of(UserEntity(id = 2L, name = null, email = null, age = null, country = null, phone = null), null),
            )
    }

    @ParameterizedTest
    @MethodSource("provideUsersForToEntity")
    fun `toEntity converts User to UserEntity preserving fields and parsing age`(
        user: User,
        expectedAge: Int?,
    ) {
        val entity = user.toEntity()

        assertEquals(user.id, entity.id)
        assertEquals(user.name, entity.name)
        assertEquals(user.email, entity.email)
        assertEquals(expectedAge, entity.age)
        assertEquals(user.country, entity.country)
        assertEquals(user.phone, entity.phone)
    }

    @ParameterizedTest
    @MethodSource("provideEntitiesForToInternalModel")
    fun `toInternalModel converts UserEntity to User and stringifies age`(
        entity: UserEntity,
        expectedAgeString: String?,
    ) {
        val user = entity.toInternalModel()

        assertEquals(entity.id!!, user.id)
        assertEquals(entity.name, user.name)
        assertEquals(entity.email, user.email)
        assertEquals(expectedAgeString, user.age)
        assertEquals(entity.country, user.country)
        assertEquals(entity.phone, user.phone)
    }
}

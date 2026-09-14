package com.planet.assessment.application.validator

import com.planet.assessment.application.TestUtils.DEFAULT_NAME
import com.planet.assessment.application.TestUtils.buildUser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class UserValidatorFactoryTest {
    @Test
    fun `getValidatorList returns only validators that shouldValidate the user`() {
        val v1 = mock<UserValidator>()
        val v2 = mock<UserValidator>()

        val user = buildUser(id = 1L, name = DEFAULT_NAME)

        whenever(v1.shouldValidate(user)).thenReturn(true)
        whenever(v2.shouldValidate(user)).thenReturn(false)

        val factory = UserValidatorFactory(listOf(v1, v2))
        val list = factory.getValidatorList(user)

        assertEquals(1, list.size)
        assertTrue(list.contains(v1))
        assertFalse(list.contains(v2))
    }
}

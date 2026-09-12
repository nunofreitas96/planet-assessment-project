package com.planet.assessment.application.validator

import com.planet.assessment.user.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class UserValidatorFactoryTest {

    @Test
    fun `getValidatorList returns only validators that shouldValidate the user`() {
        val v1 = mock<UserValidator>()
        val v2 = mock<UserValidator>()

        val user = User(id = 1L, name = "A")

        whenever(v1.shouldValidate(user)).thenReturn(true)
        whenever(v2.shouldValidate(user)).thenReturn(false)

        val factory = UserValidatorFactory(listOf(v1, v2))
        val list = factory.getValidatorList(user)

        assertEquals(1, list.size)
        assertTrue(list.contains(v1))
        assertFalse(list.contains(v2))
    }
}

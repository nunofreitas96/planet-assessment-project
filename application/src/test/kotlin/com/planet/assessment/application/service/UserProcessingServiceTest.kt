package com.planet.assessment.application.service

import com.planet.assessment.application.port.outbound.persistence.UserPersistencePort
import com.planet.assessment.application.validator.UserValidatorFactory
import com.planet.assessment.application.validator.UserValidator
import com.planet.assessment.user.User
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.kotlin.*

class UserProcessingServiceTest {

    private val persistence = mock<UserPersistencePort>()
    private val validatorFactory = mock<UserValidatorFactory>()
    private val service = UserProcessingService(persistence, validatorFactory)

    @Test
    fun `process saves new user when validators pass and no existing`() {
        val user = User(id = 1L, name = "A")
        val validator = mock<UserValidator>()

        whenever(validatorFactory.getValidatorList(any())).thenReturn(listOf(validator))
        whenever(validator.shouldValidate(any())).thenReturn(true)
        whenever(validator.validate(user)).thenReturn(true)
        whenever(persistence.findById(user.id)).thenReturn(null)

        service.process(listOf(user))

        verify(persistence).save(user)
    }

    @Test
    fun `process skips saving when a validator fails`() {
        val user = User(id = 2L, name = "B")
        val validator = mock<UserValidator>()

        whenever(validatorFactory.getValidatorList(any())).thenReturn(listOf(validator))
        whenever(validator.shouldValidate(any())).thenReturn(true)
        whenever(validator.validate(user)).thenReturn(false)

        service.process(listOf(user))

        verify(persistence, never()).save(any())
    }

    @Test
    fun `process calls findById and saves provided user when existing found`() {
        val existing = User(id = 3L, name = "Old", email = "old@example.com")
        val newUser = User(id = 3L, name = "New", email = null)
        val validator = mock<UserValidator>()

        whenever(validatorFactory.getValidatorList(any())).thenReturn(listOf(validator))
        whenever(validator.shouldValidate(any())).thenReturn(true)
        whenever(validator.validate(any())).thenReturn(true)
        whenever(persistence.findById(newUser.id)).thenReturn(existing)

        service.process(listOf(newUser))

        verify(persistence).findById(newUser.id)
        // current implementation saves the provided user
        verify(persistence).save(newUser)
    }

    @Test
    fun `consolidateUser merges fields preferring new values`() {
        val existing = User(id = 4L, name = "OldName", email = "old@example.com", age = "30", country = "PT", phone = "111")
        val newUser = User(id = 5L, name = null, email = "new@example.com", age = null, country = null, phone = "222")

        val method = UserProcessingService::class.java.getDeclaredMethod("consolidateUser", User::class.java, User::class.java)
        method.isAccessible = true
        val consolidated = method.invoke(service, existing, newUser) as User

        assertEquals(existing.id, consolidated.id)
        // name should come from newUser if present, else existing
        assertEquals("OldName", consolidated.name)
        // email should be new
        assertEquals("new@example.com", consolidated.email)
        // age preserved from existing
        assertEquals("30", consolidated.age)
        // country preserved
        assertEquals("PT", consolidated.country)
        // phone from newUser
        assertEquals("222", consolidated.phone)
    }
}

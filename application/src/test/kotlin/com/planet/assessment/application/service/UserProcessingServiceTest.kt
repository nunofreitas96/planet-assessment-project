package com.planet.assessment.application.service

import com.planet.assessment.application.TestUtils.ALT_NAME
import com.planet.assessment.application.TestUtils.DEFAULT_EMAIL
import com.planet.assessment.application.TestUtils.DEFAULT_NAME
import com.planet.assessment.application.TestUtils.NEW_NAME
import com.planet.assessment.application.TestUtils.buildUser
import com.planet.assessment.application.TestUtils.buildValidationResult
import com.planet.assessment.application.port.outbound.persistence.UserPersistencePort
import com.planet.assessment.application.validator.UserValidator
import com.planet.assessment.application.validator.UserValidatorFactory
import com.planet.assessment.user.UserDiscardReason
import com.planet.assessment.user.UserValidationResult
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class UserProcessingServiceTest {
    private val persistence = mock<UserPersistencePort>()
    private val validatorFactory = mock<UserValidatorFactory>()
    private val service = UserProcessingService(persistence, validatorFactory)

    @Test
    fun `process saves new user when validators pass and no existing`() {
        val user = buildUser(id = 1L, name = DEFAULT_NAME)
        val validator = mock<UserValidator>()

        whenever(validatorFactory.getValidatorList(any())).thenReturn(listOf(validator))
        whenever(validator.shouldValidate(any())).thenReturn(true)
        whenever(validator.validate(user)).thenReturn(buildValidationResult(isValid = true))
        whenever(persistence.findById(user.id)).thenReturn(null)

        val processResult = service.process(listOf(user))
        val expectedResult = Pair(setOf(1L), emptyList<UserValidationResult>())

        verify(persistence).save(user)
        assertEquals(expectedResult, processResult)
    }

    @Test
    fun `process skips saving when a validator fails`() {
        val user = buildUser(id = 2L, name = ALT_NAME)
        val validator = mock<UserValidator>()
        val validationResult = buildValidationResult(2L, false, UserDiscardReason.BLANK_AGE)

        whenever(validatorFactory.getValidatorList(any())).thenReturn(listOf(validator))
        whenever(validator.shouldValidate(any())).thenReturn(true)
        whenever(validator.validate(user)).thenReturn(validationResult)

        val processResult = service.process(listOf(user))
        val expectedResult = Pair(emptySet<Long>(), listOf(validationResult))

        verify(persistence, never()).save(any())
        assertEquals(expectedResult, processResult)
    }

    @Test
    fun `process calls findById and saves provided user when existing found`() {
        val existing = buildUser(id = 3L, name = DEFAULT_NAME, email = DEFAULT_EMAIL)
        val newUser = buildUser(id = 3L, name = NEW_NAME, email = null)
        val validator = mock<UserValidator>()

        whenever(validatorFactory.getValidatorList(any())).thenReturn(listOf(validator))
        whenever(validator.shouldValidate(any())).thenReturn(true)
        whenever(validator.validate(any())).thenReturn(UserValidationResult(1L, true))
        whenever(persistence.findById(newUser.id)).thenReturn(existing)

        service.process(listOf(newUser))

        verify(persistence).findById(newUser.id)
        verify(persistence).save(newUser)
    }
}

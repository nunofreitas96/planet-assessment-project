package com.planet.assessment.application.validator

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class UserValidatorBeanFactoryTest {

    @Test
    fun `getUserValidator returns factory with all validators in expected order`() {
        val ageValidator = AgeValidator()
        val emailValidator = EmailValidator()
        val phoneValidator = PhoneValidator()
        val nameValidator = NameValidator()
        val countryValidator = CountryValidator()

        val beanFactory = UserValidatorBeanFactory(
            ageValidator,
            emailValidator,
            phoneValidator,
            nameValidator,
            countryValidator
        )

        val factory = beanFactory.getUserValidator()
        val validators = factory.validators

        assertEquals(5, validators.size)
        assertSame(ageValidator, validators[0])
        assertSame(emailValidator, validators[1])
        assertSame(phoneValidator, validators[2])
        assertSame(nameValidator, validators[3])
        assertSame(countryValidator, validators[4])
    }
}

package com.planet.assessment.application.validator

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserValidatorBeanFactory(
    val ageValidator: AgeValidator,
    val emailValidator: EmailValidator,
    val phoneValidator: PhoneValidator,
    val nameValidator: NameValidator,
    val countryValidator: CountryValidator
) {
    @Bean
    fun getUserValidator(): UserValidatorFactory {
        return UserValidatorFactory(
            listOf(
                ageValidator,
                emailValidator,
                phoneValidator,
                nameValidator,
                countryValidator
            )
        )
    }

}
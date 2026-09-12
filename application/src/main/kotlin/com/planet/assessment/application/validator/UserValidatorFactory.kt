package com.planet.assessment.application.validator

import com.planet.assessment.user.User

class UserValidatorFactory(
    val validators: List<UserValidator>
) {
    fun getValidatorList(user: User): List<UserValidator> {
        return validators.filter { it.shouldValidate(user) }
    }
}
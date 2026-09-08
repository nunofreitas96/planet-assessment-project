package com.planet.assessment.application.validator

import com.planet.assessment.user.User

class UserValidatorFactory(
    val validators: List<UserValidator>
) {
    //TODO - Improves all Validators to et specific Exceptions
    fun getValidatorList(user: User): List<UserValidator> {
        return validators.filter { it.shouldValidate(user) }
    }
}
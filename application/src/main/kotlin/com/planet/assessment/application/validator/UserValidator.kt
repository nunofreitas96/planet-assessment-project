package com.planet.assessment.application.validator

import com.planet.assessment.user.User
import com.planet.assessment.user.UserValidationResult

interface UserValidator {
    fun validate(user: User): UserValidationResult

    fun shouldValidate(user: User): Boolean
}

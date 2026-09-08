package com.planet.assessment.application.validator

import com.planet.assessment.user.User

interface UserValidator {
    fun validate(user: User): Boolean
    fun shouldValidate(user: User): Boolean
}
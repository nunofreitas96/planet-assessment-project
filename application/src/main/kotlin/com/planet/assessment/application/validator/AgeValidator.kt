package com.planet.assessment.application.validator

import com.planet.assessment.user.User
import org.springframework.stereotype.Service

@Service
class AgeValidator : UserValidator {
    override fun validate(user: User): Boolean {
        val age = user.age!!
        return age.isNotBlank() && age.toIntOrNull() != null
    }

    override fun shouldValidate(user: User): Boolean {
        return user.age == null
    }
}
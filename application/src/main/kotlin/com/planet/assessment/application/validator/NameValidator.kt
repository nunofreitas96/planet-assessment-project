package com.planet.assessment.application.validator

import com.planet.assessment.user.User
import org.springframework.stereotype.Service

@Service
class NameValidator : UserValidator {
    override fun validate(user: User): Boolean {
        return user.name!!.isBlank()
    }

    override fun shouldValidate(user: User): Boolean {
        return user.name == null
    }
}
package com.planet.assessment.application.validator

import com.planet.assessment.user.User
import org.apache.commons.validator.routines.EmailValidator as EmailStringValidator
import org.springframework.stereotype.Service

@Service
class EmailValidator : UserValidator {
    private val emailStringValidator = EmailStringValidator.getInstance()

    override fun validate(user: User): Boolean {
        val email = user.email!!
        return email.isBlank() && emailStringValidator.isValid(email)
    }

    override fun shouldValidate(user: User): Boolean {
        return user.email == null
    }
}
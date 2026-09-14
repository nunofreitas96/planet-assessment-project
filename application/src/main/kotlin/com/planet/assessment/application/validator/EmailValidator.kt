package com.planet.assessment.application.validator

import com.planet.assessment.user.User
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.apache.commons.validator.routines.EmailValidator as EmailStringValidator

@Service
class EmailValidator : UserValidator {
    private val logger = LoggerFactory.getLogger(this::class.java)

    private val emailStringValidator = EmailStringValidator.getInstance()

    override fun validate(user: User): Boolean {
        val email = user.email!!
        val isValid = email.isNotBlank() && emailStringValidator.isValid(email)
        if (!isValid) {
            if (email.isBlank()) {
                logger.warn("Discarding User with id ${user.id} due to blank email column.")
            } else {
                logger.warn("Discarding User with id ${user.id} due to invalid email: $email")
            }
        }
        return isValid
    }

    override fun shouldValidate(user: User): Boolean = user.email != null
}

package com.planet.assessment.application.validator

import com.planet.assessment.user.User
import com.planet.assessment.user.UserDiscardReason
import com.planet.assessment.user.UserValidationResult
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.apache.commons.validator.routines.EmailValidator as EmailStringValidator

@Service
class EmailValidator : UserValidator {
    private val logger = LoggerFactory.getLogger(this::class.java)

    private val emailStringValidator = EmailStringValidator.getInstance()

    override fun validate(user: User): UserValidationResult {
        val email = user.email!!
        val isValid = email.isNotBlank() && emailStringValidator.isValid(email)
        if (!isValid) {
            if (email.isBlank()) {
                logger.warn("Discarding User with id ${user.id} due to blank email column.")
                return UserValidationResult(user.id, false, UserDiscardReason.BLANK_EMAIL)
            } else {
                logger.warn("Discarding User with id ${user.id} due to invalid email: $email")
                return UserValidationResult(user.id, false, UserDiscardReason.INVALID_EMAIL)
            }
        }
        return UserValidationResult(user.id, true)
    }

    override fun shouldValidate(user: User): Boolean = user.email != null
}

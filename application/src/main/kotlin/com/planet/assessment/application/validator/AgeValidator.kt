package com.planet.assessment.application.validator

import com.planet.assessment.user.User
import com.planet.assessment.user.UserDiscardReason
import com.planet.assessment.user.UserValidationResult
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class AgeValidator : UserValidator {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun validate(user: User): UserValidationResult {
        val age = user.age!!
        val isValid = age.isNotBlank() && age.toIntOrNull() != null

        if (!isValid) {
            if (age.isBlank()) {
                logger.warn("Discarding User with id ${user.id} due to blank age column.")
                return UserValidationResult(user.id, false, UserDiscardReason.BLANK_AGE)
            } else {
                logger.warn("Discarding User with id ${user.id} due to invalid age: $age")
                return UserValidationResult(user.id, false, UserDiscardReason.INVALID_AGE)
            }
        }
        return UserValidationResult(user.id, true)
    }

    override fun shouldValidate(user: User): Boolean = user.age != null
}

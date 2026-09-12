package com.planet.assessment.application.validator

import com.planet.assessment.user.User
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class AgeValidator : UserValidator {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun validate(user: User): Boolean {
        val age = user.age!!
        val isValid = age.isNotBlank() && age.toIntOrNull() != null

        if (!isValid) {
            if (age.isBlank()) {
                logger.warn("Discarding User with id ${user.id} due to blank age column.")
            } else {
                logger.warn("Discarding User with id ${user.id} due to invalid age: $age")
            }
        }
        return isValid
    }

    override fun shouldValidate(user: User): Boolean {
        return user.age != null
    }
}
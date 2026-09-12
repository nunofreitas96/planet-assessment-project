package com.planet.assessment.application.validator

import com.planet.assessment.user.User
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class NameValidator : UserValidator {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun validate(user: User): Boolean {
        val name = user.name!!
        val isValid = name.isNotBlank()
        if (!isValid) {
            logger.warn("Discarding User with id ${user.id} due to blank name column.")
        }
        return isValid
    }

    override fun shouldValidate(user: User): Boolean {
        return user.name != null
    }
}
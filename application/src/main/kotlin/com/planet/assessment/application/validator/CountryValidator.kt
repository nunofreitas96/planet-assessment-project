package com.planet.assessment.application.validator

import com.planet.assessment.user.User
import com.planet.assessment.user.UserDiscardReason
import com.planet.assessment.user.UserValidationResult
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CountryValidator : UserValidator {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun validate(user: User): UserValidationResult {
        val country = user.country!!
        val isValid = country.isNotBlank()
        if (!isValid) {
            logger.warn("Discarding User with id ${user.id} due to blank country column.")
            return UserValidationResult(user.id, false, UserDiscardReason.BLANK_COUNTRY)
        }
        return UserValidationResult(user.id, true)
    }

    override fun shouldValidate(user: User): Boolean = user.country != null
}

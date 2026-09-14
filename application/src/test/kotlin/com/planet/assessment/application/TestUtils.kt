package com.planet.assessment.application

import com.planet.assessment.user.User
import com.planet.assessment.user.UserDiscardReason
import com.planet.assessment.user.UserValidationResult

object TestUtils {
    const val DEFAULT_NAME: String = "John"
    const val ALT_NAME: String = "Bob"
    const val NEW_NAME: String = "Jonathan"

    const val DEFAULT_EMAIL: String = "john@example.com"
    const val ALT_EMAIL: String = "bob@example.com"

    const val DEFAULT_CSV_LINE = "1,John,john@example.com"
    const val ALT_CSV_LINE = "2,Bob,bob@example.com"

    const val DEFAULT_AGE: String = "30"
    const val DEFAULT_COUNTRY: String = "Portugal"
    const val DEFAULT_PHONE: String = "912345678"
    const val DEFAULT_PHONE_WITH_IDENTIFIER: String = "+351912345678"

    const val ID_COLUMN = "ID"
    const val NAME_COLUMN = "NAME"
    const val EMAIL_COLUMN = "EMAIL"

    fun buildUser(
        id: Long,
        name: String? = DEFAULT_NAME,
        email: String? = DEFAULT_EMAIL,
        age: String? = DEFAULT_AGE,
        country: String? = DEFAULT_COUNTRY,
        phone: String? = DEFAULT_PHONE,
    ): User = User(id = id, name = name, email = email, age = age, country = country, phone = phone)

    fun buildValidationResult(
        id: Long = 1L,
        isValid: Boolean,
        discardReason: UserDiscardReason? = null,
    ): UserValidationResult = UserValidationResult(userId = id, isValid = isValid, discardReason = discardReason)
}

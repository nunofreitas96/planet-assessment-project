package com.planet.assessment.user

data class UserValidationResult(
    val userId: Long,
    val isValid: Boolean,
    val discardReason: UserDiscardReason? = null
)

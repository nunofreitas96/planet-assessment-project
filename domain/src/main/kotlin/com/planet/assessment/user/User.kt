package com.planet.assessment.user

import java.util.UUID

data class User(
    val id: UUID,
    val externalId: Long,
    val name: String? = null,
    val email: String? = null,
    val age: Int? = null,
    val country: String? = null,
    val phone: String? = null,
)

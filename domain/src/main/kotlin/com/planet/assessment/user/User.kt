package com.planet.assessment.user

data class User(
    val id: Long,
    val name: String? = null,
    val email: String? = null,
    val age: String? = null,
    val country: String? = null,
    val phone: String? = null,
)

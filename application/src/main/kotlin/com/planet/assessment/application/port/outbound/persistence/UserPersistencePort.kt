package com.planet.assessment.application.port.outbound.persistence

import com.planet.assessment.user.User

interface UserPersistencePort {
    fun save(user: User): User
    fun findById(id: Long): User?
    fun findAll(): List<User>
}
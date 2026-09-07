package com.planet.assessment.application.port.outbound.persistence

import com.planet.assessment.user.User

interface UserPersistencePort {
    fun save(user: User): User
    fun findByExternalId(externalId: Long): User?
}
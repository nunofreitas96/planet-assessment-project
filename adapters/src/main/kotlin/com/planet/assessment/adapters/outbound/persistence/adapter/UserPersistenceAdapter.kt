package com.planet.assessment.adapters.outbound.persistence.adapter

import com.planet.assessment.adapters.outbound.persistence.mapper.UserMapper.toEntity
import com.planet.assessment.adapters.outbound.persistence.mapper.UserMapper.toInternalModel
import com.planet.assessment.adapters.outbound.persistence.repository.UserRepository
import com.planet.assessment.application.port.outbound.persistence.UserPersistencePort
import com.planet.assessment.user.User
import org.springframework.stereotype.Service

@Service
class UserPersistenceAdapter(
    private val userRepository: UserRepository
): UserPersistencePort {
    override fun save(user: User): User {
        return userRepository.save(user.toEntity()).toInternalModel()
    }

    override fun findById(id: Long): User? {
        return userRepository.findById(id).orElse(null)?.toInternalModel()
    }

}
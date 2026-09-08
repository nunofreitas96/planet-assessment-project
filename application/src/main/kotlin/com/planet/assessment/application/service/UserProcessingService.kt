package com.planet.assessment.application.service

import com.planet.assessment.application.port.inbound.service.UserProcessingServicePort
import com.planet.assessment.application.port.outbound.persistence.UserPersistencePort
import com.planet.assessment.application.validator.UserValidatorFactory
import com.planet.assessment.user.User
import org.springframework.stereotype.Service

@Service
class UserProcessingService(
    private val userPersistencePort: UserPersistencePort,
    private val userValidatorFactory: UserValidatorFactory
) : UserProcessingServicePort {

    override fun process(users: List<User>) {
        users.forEach { user ->
            userValidatorFactory.getValidatorList(user).forEach { validator ->
                if(!validator.validate(user)) return
            }
            val existingUser = userPersistencePort.findById(user.id)
            if (existingUser != null) {
                consolidateUser(existingUser = existingUser, newUser = user)

            }
            userPersistencePort.save(user)
        }
    }

    private fun consolidateUser(
        existingUser: User,
        newUser: User,
    ) : User{
        return User(
            id = existingUser.id,
            name = newUser.name ?: existingUser.name,
            email = newUser.email ?: existingUser.email,
            age = newUser.age ?: existingUser.age,
            country = newUser.country ?: existingUser.country,
            phone = newUser.phone ?: existingUser.phone
        )
    }
}
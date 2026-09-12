package com.planet.assessment.application.service

import com.planet.assessment.application.port.inbound.service.UserProcessingServicePort
import com.planet.assessment.application.port.outbound.persistence.UserPersistencePort
import com.planet.assessment.application.validator.UserValidatorBeanFactory
import com.planet.assessment.application.validator.UserValidatorFactory
import com.planet.assessment.user.User
import org.springframework.stereotype.Service

@Service
class UserProcessingService(
    private val userPersistencePort: UserPersistencePort,
    private val userValidatorFactory: UserValidatorFactory
) : UserProcessingServicePort {

    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)

    override fun process(users: List<User>) {
        val validators = userValidatorFactory.getValidatorList(users.first())

        users.forEach { user ->
            if(!validators.all { validator ->
                validator.validate(user)
            }) return@forEach

            val existingUser = userPersistencePort.findById(user.id)
            if (existingUser != null) {
                consolidateUser(existingUser = existingUser, newUser = user)
                logger.info("Updating user with id ${user.id}")
            } else {
                logger.info("Saving user with id ${user.id}")
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
package com.planet.assessment.adapters.outbound.persistence.mapper

import com.planet.assessment.adapters.outbound.persistence.entity.UserEntity
import com.planet.assessment.user.User


object UserMapper {

    fun User.toEntity(): UserEntity {
        return UserEntity(
            id = this.id,
            externalId = this.externalId,
            name = this.name,
            email = this.email,
            age = this.age,
            country = this.country,
            phone = this.phone
        )
    }

    fun UserEntity.toInternalModel(): User {
        return User(
            id = this.id,
            externalId = this.externalId,
            name = this.name,
            email = this.email,
            age = this.age,
            country = this.country,
            phone = this.phone
        )
    }

}
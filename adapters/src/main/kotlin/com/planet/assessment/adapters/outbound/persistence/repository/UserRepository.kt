package com.planet.assessment.adapters.outbound.persistence.repository

import com.planet.assessment.adapters.outbound.persistence.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Long>

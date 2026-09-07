package com.planet.assessment.adapters.outbound.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false, unique = true)
    val externalId: Long,

    @Column(nullable = true, unique = false)
    val name: String? = null,


    @Column(nullable = true, unique = false)
    val email: String? = null,

    @Column(nullable = true, unique = false)
    val age: Int? = null,

    @Column(nullable = true, unique = false)
    val country: String? = null,

    @Column(nullable = true, unique = false)
    val phone: String? = null,
)
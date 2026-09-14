package com.planet.assessment.adapters.outbound.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    var id: Long? = null,
    @Column(nullable = true, unique = false)
    var name: String? = null,
    @Column(nullable = true, unique = false)
    var email: String? = null,
    @Column(nullable = true, unique = false)
    var age: Int? = null,
    @Column(nullable = true, unique = false)
    var country: String? = null,
    @Column(nullable = true, unique = false)
    var phone: String? = null,
)

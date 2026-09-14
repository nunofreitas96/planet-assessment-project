package com.planet.assessment.application.port.inbound.service

import com.planet.assessment.user.User

interface UserProcessingServicePort {
    fun process(users: List<User>)
}

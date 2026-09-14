package com.planet.assessment.application.port.inbound.service

import com.planet.assessment.user.User
import com.planet.assessment.user.UserValidationResult

interface UserProcessingServicePort {
    fun process(users: List<User>): Pair<Set<Long>, List<UserValidationResult>>
}

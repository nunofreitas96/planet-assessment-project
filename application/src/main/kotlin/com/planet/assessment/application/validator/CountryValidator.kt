package com.planet.assessment.application.validator

import com.planet.assessment.user.User
import org.springframework.stereotype.Service

@Service
class CountryValidator : UserValidator  {
    override fun validate(user: User): Boolean {
        return user.country!!.isBlank()
    }

    override fun shouldValidate(user: User): Boolean {
        return user.country == null
    }
}
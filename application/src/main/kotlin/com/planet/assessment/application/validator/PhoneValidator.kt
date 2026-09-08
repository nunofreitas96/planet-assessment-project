package com.planet.assessment.application.validator

import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber.CountryCodeSource
import com.planet.assessment.user.User
import org.springframework.stereotype.Service


@Service
class PhoneValidator : UserValidator  {
    private val phoneNumberUtil: PhoneNumberUtil = PhoneNumberUtil.getInstance()


    override fun validate(user: User): Boolean {
        val userPhone = user.phone!!

        val phone = phoneNumberUtil.parse(userPhone,
            CountryCodeSource.UNSPECIFIED.toString());


        return phoneNumberUtil.isValidNumber(phone) && userPhone.isNotBlank()
    }

    override fun shouldValidate(user: User): Boolean {
        return user.country == null
    }
}
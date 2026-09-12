package com.planet.assessment.application.validator

import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber.CountryCodeSource
import com.planet.assessment.user.User
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class PhoneValidator : UserValidator  {
    private val logger = LoggerFactory.getLogger(this::class.java)

    private val phoneNumberUtil: PhoneNumberUtil = PhoneNumberUtil.getInstance()


    override fun validate(user: User): Boolean {
        val userPhone = user.phone!!

        val phone = phoneNumberUtil.parse(userPhone,
            "PT");

        val isValid = phoneNumberUtil.isValidNumber(phone) && userPhone.isNotBlank()
        if (!isValid) {
            if (userPhone.isBlank()) {
                logger.warn("Discarding User with id ${user.id} due to blank phone column.")
            } else
            logger.warn("Discarding User with id ${user.id} due to invalid phone number: $userPhone.")
        }
        return isValid
    }

    override fun shouldValidate(user: User): Boolean {
        return user.phone != null
    }
}
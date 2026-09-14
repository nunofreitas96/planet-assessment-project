package com.planet.assessment.user

enum class UserDiscardReason(val reasonString: String) {
    BLANK_NAME("User discarded due to 'name' being blank."),
    INVALID_EMAIL("User discarded due to 'email' not being valid."),
    BLANK_EMAIL("User discarded due to 'email' being blank."),
    INVALID_AGE("User discarded due to 'age' not being valid."),
    BLANK_AGE("User discarded due to 'age' being blank."),
    BLANK_COUNTRY("User discarded due to 'country' being blank."),
    INVALID_PHONE("User discarded due to 'phone' not being valid."),
    BLANK_PHONE("User discarded due to 'phone' being blank."),

}
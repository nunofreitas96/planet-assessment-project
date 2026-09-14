package com.planet.assessment

import com.planet.assessment.user.User

object TestUtils {
    const val DEFAULT_NAME = "John"
    const val ALT_NAME: String = "Bob"
    const val DEFAULT_EMAIL = "john@example.com"
    const val ALT_EMAIL: String = "bob@example.com"
    const val DEFAULT_AGE_COLUMN = "25"
    const val DEFAULT_COUNTRY = "Portugal"
    const val DEFAULT_PHONE = "912345678"

    const val PATH_EXPORT_DATA: String = "/api/v1/export"
    const val PATH_IMPORT_CSV: String = "/api/v1/import/csv"

    const val CSV_TEXT = "id,name,email\n1,John,john@example.com\n2,Bob,bob@example.com\n"
    const val CSV_TEXT_NO_ID = "name,email\nJohn,john@example.com\n"
    const val CSV_WITH_DUPLICATE_COLUMNS = "id,name,name\n1,John,JohnDuplicate\n"
    const val FILE_NAME = "file"
    const val ORIGINAL_FILE_NAME = "customers.csv"
    const val CONTENT_TYPE = "text/csv"

    fun buildUser(
        id: Long,
        name: String? = DEFAULT_NAME,
        email: String? = DEFAULT_EMAIL,
        age: String? = DEFAULT_AGE_COLUMN,
        country: String? = DEFAULT_COUNTRY,
        phone: String? = DEFAULT_PHONE,
    ): User = User(id = id, name = name, email = email, age = age, country = country, phone = phone)
}

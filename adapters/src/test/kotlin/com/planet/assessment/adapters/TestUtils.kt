package com.planet.assessment.adapters

object TestUtils {
    const val DEFAULT_NAME = "John"
    const val ALT_NAME: String = "Bob"
    const val DEFAULT_EMAIL = "john@example.com"
    const val ALT_EMAIL: String = "bob@example.com"
    const val DEFAULT_AGE = 25
    const val DEFAULT_AGE_COLUMN = "25"
    const val DEFAULT_COUNTRY = "Portugal"
    const val DEFAULT_PHONE = "912345678"

    const val WRONG_AGE = "twenty five"
    const val BLANK_COLUMN = ""

    const val CSV_TEXT = "id,name,email\n1,John,\"john@example.com\"\n2,Bob,bob@example.com\n"
    const val CSV_TEXT_NO_ID = "name,email\nJohn,john@example.com\n"
    const val CSV_WITH_DUPLICATE_COLUMNS = "id,name,name\n1,John,JohnDuplicate\n"
    const val CSV_WITH_INVALID_ID = "id,name,email\nabc,Invalid,invalid@example.com\n2,Bob,bob@example.com\n"
    const val FILE_NAME = "file"
    const val ORIGINAL_FILE_NAME = "customers.csv"
    const val CONTENT_TYPE = "text/csv"
}

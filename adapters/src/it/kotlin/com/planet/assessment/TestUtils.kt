package com.planet.assessment

object TestUtils {
    const val PATH_EXPORT_DATA: String = "/api/v1/export"
    const val PATH_IMPORT_CSV: String = "/api/v1/import/csv"

    const val CSV_TEXT = "id,name,email\n1,John,john@example.com\n2,Bob,bob@example.com\n"
    const val CSV_TEXT_NO_ID = "name,email\nJohn,john@example.com\n"
    const val CSV_WITH_DUPLICATE_COLUMNS = "id,name,name\n1,John,JohnDuplicate\n"
    const val FILE_NAME = "file"
    const val ORIGINAL_FILE_NAME = "customers.csv"
    const val CONTENT_TYPE = "text/csv"
}

package com.planet.assessment.application.exception

class MissingColumnValueException(
    val missingColumn: String,
    val id: Long? = null
) : IllegalArgumentException("Missing column value for column: $missingColumn")
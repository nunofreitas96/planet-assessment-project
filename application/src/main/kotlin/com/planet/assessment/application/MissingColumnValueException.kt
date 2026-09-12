package com.planet.assessment.application

class MissingColumnValueException(
    val missingColumn: String,
    val id: Long? = null
) : IllegalArgumentException("Missing column value for column: $missingColumn")
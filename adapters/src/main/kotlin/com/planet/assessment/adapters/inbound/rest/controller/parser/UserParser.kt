package com.planet.assessment.adapters.inbound.rest.controller.parser

import com.planet.assessment.application.exception.MissingColumnValueException
import com.planet.assessment.user.User
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVRecord
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException

object UserParser {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun parseUsers(file: MultipartFile): List<User> {
        val parser =
            CSVFormat.DEFAULT
                .builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .get()
                .parse(file.inputStream.bufferedReader())

        val columnHeaders = parser.headerNames

        if (!columnHeaders.contains("id")) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Missing required column - id",
            )
        }

        val duplicates =
            columnHeaders
                .groupingBy { it }
                .eachCount()
                .filter { (_, count) -> count > 1 }
                .keys

        if (duplicates.isNotEmpty()) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "CSV has duplicate columns: ${duplicates.joinToString(",")}",
            )
        }

        return parser.records.mapNotNull { record ->
            try {
                val id = record.getOrNull("id")

                id?.toLongOrNull()?.let {
                    User(
                        id = it,
                        name = record.getOrNull("name", it),
                        email = record.getOrNull("email", it),
                        age = record.getOrNull("age", it),
                        country = record.getOrNull("country", it),
                        phone = record.getOrNull("phone", it),
                    )
                } ?: run {
                    logger.error("Invalid id for user record. Id: $id")
                    null
                }
            } catch (e: MissingColumnValueException) {
                e.id?.let {
                    logger.error("Discarding user due to missing column value for column ${e.missingColumn} on id: ${e.id}")
                } ?: logger.error("Discarding user due to missing column value for id")
                null
            }
        }
    }

    private fun CSVRecord.getOrNull(
        column: String,
        id: Long? = null,
    ): String? {
        try {
            return if (this.isMapped(column)) {
                this.get(column)
            } else {
                null
            }
        } catch (e: IllegalArgumentException) {
            throw MissingColumnValueException(
                missingColumn = column,
                id = id,
            )
        }
    }
}

package com.planet.assessment.adapters.inbound.rest.controller

import com.planet.assessment.application.MissingColumnValueException
import com.planet.assessment.application.port.inbound.service.UserProcessingServicePort
import com.planet.assessment.application.port.inbound.service.UserRetrievalServicePort
import com.planet.assessment.column.ExportColumn
import com.planet.assessment.format.ExportFormat as DomainExportFormat
import com.planet.assessment.user.User
import com.planetassessment.adapters.api.UserFileApi
import com.planetassessment.adapters.model.ExportFormat
import com.planetassessment.adapters.model.UploadResponse
import com.planetassessment.adapters.model.UploadResponse.Status
import io.micrometer.observation.annotation.Observed
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVRecord
import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class UserFileController(
    private val userProcessingServicePort: UserProcessingServicePort,
    private val userRetrievalServicePort: UserRetrievalServicePort
) : UserFileApi {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Observed(name = "import.csv")
    override fun importCsv(file: MultipartFile): ResponseEntity<UploadResponse> {
        logger.info("Received file: ${file.originalFilename}, size: ${file.size} bytes")
        try{
            val users = parseUsers(file)
            userProcessingServicePort.process(users)

            return ResponseEntity.ok(UploadResponse(status = Status.success, message = "File uploaded successfully"))
        } catch (e: Exception) {
            logger.error("Error occurred while processing file: ${file.originalFilename}", e)
            return ResponseEntity.status(500).body(
                UploadResponse(
                    status = Status.failure,
                    message = "Error occurred while processing file"
                )
            )
        }
    }


    override fun exportData(format: ExportFormat, columns: String): ResponseEntity<Resource> {
        val columnStrings = columns
            .split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }

        val selectedColumns = columnStrings
            .map { ExportColumn.valueOf(it.uppercase()) }

        //TODO - Create proper mapper for it
        val domainFormat = when (format) {
            ExportFormat.csv -> DomainExportFormat.CSV
            ExportFormat.txt -> DomainExportFormat.TXT
            ExportFormat.xls -> DomainExportFormat.XLS
            ExportFormat.xlsx -> DomainExportFormat.XLSX
        }

        val mediaType = when (format) {
            ExportFormat.csv -> MediaType.parseMediaType("text/csv")
            ExportFormat.txt -> MediaType.TEXT_PLAIN
            ExportFormat.xls -> MediaType.parseMediaType("application/vnd.ms-excel")
            ExportFormat.xlsx -> MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
        }

        val resource = userRetrievalServicePort.retrieveUsers(domainFormat, selectedColumns)

        return ResponseEntity.ok().contentType(mediaType)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.${format.value}")
            .body(resource)
    }


    private fun parseUsers(file: MultipartFile): List<User> {
        val parser = CSVFormat.DEFAULT.builder()
            .setHeader()
            .setSkipHeaderRecord(true)
            .get()
            .parse(file.inputStream.bufferedReader())

        if(!parser.headerNames.contains("id")){
            throw IllegalArgumentException("Missing required column: id")
        }

        return parser.records.mapNotNull { record ->
            try {
                val id = record.getOrNull("id")

                id?.toLongOrNull()?.let { User(
                    id = it,
                    name = record.getOrNull("name",it),
                    email = record.getOrNull("email",it),
                    age = record.getOrNull("age",it),
                    country = record.getOrNull("country",it),
                    phone = record.getOrNull("phone",it)
                ) } ?: run {
                    logger.error("Invalid id for user record. Id: $id")
                    null
                }

            } catch (e: MissingColumnValueException) {
                e.id?.let { logger.error("Discarding user due to missing column value for column ${e.missingColumn} on id: ${e.id}")
                } ?: logger.error("Discarding user due to missing column value for id")
                null
            }
        }
    }

    private fun CSVRecord.getOrNull(
        column: String,
        id: Long? = null
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
                id = id

            )
        }

    }


}
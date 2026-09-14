package com.planet.assessment.adapters.inbound.rest.controller

import com.planet.assessment.adapters.inbound.rest.controller.mapper.ExportFormatMapper.toDomainExportFormat
import com.planet.assessment.adapters.inbound.rest.controller.mapper.ExportFormatMapper.toMediaType
import com.planet.assessment.adapters.inbound.rest.controller.parser.UserParser.parseUsers
import com.planet.assessment.application.port.inbound.service.UserProcessingServicePort
import com.planet.assessment.application.port.inbound.service.UserRetrievalServicePort
import com.planet.assessment.column.ExportColumn
import com.planetassessment.adapters.api.UserFileApi
import com.planetassessment.adapters.model.ExportFormat
import com.planetassessment.adapters.model.UploadResponse
import com.planetassessment.adapters.model.UploadResponse.Status
import io.micrometer.observation.annotation.Observed
import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException

@RestController
class UserFileController(
    private val userProcessingServicePort: UserProcessingServicePort,
    private val userRetrievalServicePort: UserRetrievalServicePort,
) : UserFileApi {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Observed(name = "import.csv")
    override fun importCsv(file: MultipartFile): ResponseEntity<UploadResponse> {
        logger.info("Received file: ${file.originalFilename}, size: ${file.size} bytes")
        try {
            val users = parseUsers(file)
            userProcessingServicePort.process(users)

            return ResponseEntity.ok(UploadResponse(status = Status.success, message = "File uploaded successfully"))
        } catch (e: ResponseStatusException) {
            logger.error("Error while importing CSV with invalid format:\n" + e.message)
            throw e
        } catch (e: Exception) {
            logger.error("Error occurred while processing file: ${file.originalFilename}", e)
            throw ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error occurred while processing file",
                e,
            )
        }
    }

    override fun exportData(
        format: ExportFormat,
        columns: List<String>,
    ): ResponseEntity<Resource> {
        logger.info(
            "Received request to export ${format.value} file with columns ${columns.joinToString(",")}",
        )

        try {
            validateRequestedColumns(columns)
            val selectedColumns =
                columns.map { column ->
                    ExportColumn.entries
                        .firstOrNull { it.name == column.uppercase() }
                        ?: throw ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Invalid column requested: $column. Allowed: ${ExportColumn.entries.joinToString { it.name }}",
                        )
                }

            val domainFormat = format.toDomainExportFormat()
            val resource = userRetrievalServicePort.retrieveUsers(domainFormat, selectedColumns)

            val mediaType = format.toMediaType()
            return ResponseEntity
                .ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.${format.value}")
                .body(resource)
        } catch (e: ResponseStatusException) {
            logger.error("Invalid export request: ${e.reason}", e)
            throw e
        } catch (e: Exception) {
            logger.error("Error occurred while exporting data", e)
            throw ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error occurred while exporting file",
                e,
            )
        }
    }

    private fun validateRequestedColumns(columns: List<String>) {
        columns.forEach { column ->
            val normalized = column.uppercase()
            ExportColumn.entries
                .firstOrNull { it.name == normalized }
                ?: throw ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid column requested : $column. Allowed: ${ExportColumn.entries.joinToString { it.name }}",
                )
        }
    }
}

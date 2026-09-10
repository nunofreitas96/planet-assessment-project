package com.planet.assessment.adapters.inbound.rest.controller

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
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class UserFileController(
    private val userProcessingServicePort: UserProcessingServicePort,
    private val userRetrievalServicePort: UserRetrievalServicePort
) : UserFileApi {

    @Observed(name = "import.csv")
    override fun importCsv(file: MultipartFile): ResponseEntity<UploadResponse> {
        // service.importCsv(file)

        val users = parseUsers(file)
        userProcessingServicePort.process(users)

        return ResponseEntity.ok(UploadResponse(status = Status.success, message = "File uploaded successfully"))
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

        val resource = userRetrievalServicePort.retrieveUsers(domainFormat, selectedColumns)

        return ResponseEntity.ok(resource)
    }


    private fun parseUsers(file: MultipartFile): List<User> {
        val parser = CSVFormat.DEFAULT.builder()
            .setHeader()
            .setSkipHeaderRecord(true)
            .get()
            .parse(file.inputStream.bufferedReader())

        return parser.records.mapNotNull { record ->
            //TODO - Ensure that missing records are added to error
            val id = record.getOrNull("id")?.toLongOrNull()
            id?.let { User(
                id = id,
                name = record.getOrNull("name"),
                email = record.getOrNull("email"),
                age = record.getOrNull("age"),
                country = record.getOrNull("country"),
                phone = record.getOrNull("phone")
            ) }

        }
    }

    private fun CSVRecord.getOrNull(column: String): String? {
        return if( this.isMapped(column)) {
            this.get(column)
        } else {
            null
        }
    }


}
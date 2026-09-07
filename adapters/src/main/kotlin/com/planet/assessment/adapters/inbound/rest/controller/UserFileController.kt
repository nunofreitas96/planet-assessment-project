package com.planet.assessment.adapters.inbound.rest.controller

import com.planet.assessment.user.User
import com.planetassessment.adapters.api.UserFileApi
import com.planetassessment.adapters.model.UploadResponse
import com.planetassessment.adapters.model.UploadResponse.Status
import io.micrometer.observation.annotation.Observed
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVRecord
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@RestController
class UserFileController() : UserFileApi {

    @Observed(name = "import.csv")
    override fun importCsv(file: MultipartFile): ResponseEntity<UploadResponse> {
        // service.importCsv(file)

        val users = parseUsers(file)



        return ResponseEntity.ok(UploadResponse(status = Status.success, message = "File uploaded successfully"))
    }

    private fun parseUsers(file: MultipartFile): List<User> {
        val parser = CSVFormat.DEFAULT.builder()
            .setHeader()
            .setSkipHeaderRecord(true)
            .get()
            .parse(file.inputStream.bufferedReader())

        return parser.records.map { record ->
            User(
                id = UUID.randomUUID(),
                externalId = record.get("externalId").toLong(),
                name = record.getOptional("name"),
                email = record.getOptional("email"),
                age = record.getOptional("age")?.toInt(),
                country = record.getOptional("country"),
                phone = record.getOptional("phone")
            )
        }
    }

    private fun CSVRecord.getOptional(column: String): String? {
        return if( this.isMapped(column)) {
            this.get(column)
        } else {
            null
        }
    }


}
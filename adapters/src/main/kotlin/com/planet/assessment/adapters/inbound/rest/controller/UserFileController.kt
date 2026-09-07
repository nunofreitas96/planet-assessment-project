package com.planet.assessment.adapters.inbound.rest.controller

import com.planetassessment.adapters.api.UserFileApi
import com.planetassessment.adapters.model.UploadResponse
import com.planetassessment.adapters.model.UploadResponse.Status
import io.micrometer.observation.annotation.Observed
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class UserFileController() : UserFileApi {

    @Observed(name = "import.csv")
    override fun importCsv(file: MultipartFile): ResponseEntity<UploadResponse> {
        // service.importCsv(file)

        return ResponseEntity.ok(UploadResponse(status = Status.success, message = "File uploaded successfully"))
    }


}
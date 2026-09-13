package com.planet.assessment.adapters.inbound.rest.controller

import com.planet.assessment.adapters.TestUtils.CONTENT_TYPE
import com.planet.assessment.adapters.TestUtils.CSV_TEXT
import com.planet.assessment.adapters.TestUtils.CSV_TEXT_NO_ID
import com.planet.assessment.adapters.TestUtils.FILE_NAME
import com.planet.assessment.adapters.TestUtils.ORIGINAL_FILE_NAME
import com.planet.assessment.adapters.TestUtils.DEFAULT_NAME
import com.planet.assessment.adapters.inbound.rest.controller.mapper.ExportFormatMapper.toMediaType
import com.planet.assessment.application.port.inbound.service.UserProcessingServicePort
import com.planet.assessment.application.port.inbound.service.UserRetrievalServicePort
import com.planet.assessment.format.ExportFormat as DomainExportFormat
import com.planetassessment.adapters.model.ExportFormat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.*
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.server.ResponseStatusException

class UserFileControllerTest {

    private val processing = mock<UserProcessingServicePort>()
    private val retrieval = mock<UserRetrievalServicePort>()
    private val controller = UserFileController(processing, retrieval)

    @Test
    fun `importCsv should parse file and call processing service and return OK`() {
        val csv = CSV_TEXT
        val file = MockMultipartFile(FILE_NAME, ORIGINAL_FILE_NAME, CONTENT_TYPE, csv.toByteArray())

        val response = controller.importCsv(file)

        assertEquals(HttpStatus.OK, response.statusCode)

        verify(processing).process(check {
            assertEquals(2, it.size)
            assertEquals(1L, it[0].id)
            assertEquals(DEFAULT_NAME, it[0].name)
        })
    }

    @Test
    fun `importCsv should throw BAD_REQUEST when id header missing`() {
        val csv = CSV_TEXT_NO_ID
        val file = MockMultipartFile(FILE_NAME, ORIGINAL_FILE_NAME, CONTENT_TYPE, csv.toByteArray())

        assertThrows<ResponseStatusException> {
            controller.importCsv(file)
        }
    }

    @Test
    fun `exportData should delegate to retrieval and return resource with media type`() {
        val resource = ByteArrayResource("data".toByteArray())
        whenever(retrieval.retrieveUsers(eq(DomainExportFormat.CSV), any())).thenReturn(resource)

        val result = controller.exportData(ExportFormat.csv, listOf("id"))

        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(ExportFormat.csv.toMediaType().toString(), result.headers.contentType.toString())
        verify(retrieval).retrieveUsers(eq(DomainExportFormat.CSV), any())
    }
}

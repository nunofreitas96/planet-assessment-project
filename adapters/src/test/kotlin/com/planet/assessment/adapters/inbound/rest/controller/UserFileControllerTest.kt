package com.planet.assessment.adapters.inbound.rest.controller

import com.planet.assessment.adapters.inbound.rest.controller.mapper.ExportFormatMapper.toMediaType
import com.planet.assessment.application.port.inbound.service.UserProcessingServicePort
import com.planet.assessment.application.port.inbound.service.UserRetrievalServicePort
import com.planet.assessment.column.ExportColumn
import com.planet.assessment.format.ExportFormat as DomainExportFormat
import com.planet.assessment.user.User
import com.planetassessment.adapters.model.ExportFormat
import com.planetassessment.adapters.model.UploadResponse
import org.junit.jupiter.api.Assertions.*
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
        val csv = "id,name,email\n1,Alice,alice@example.com\n2,Bob,bob@example.com\n"
        val file = MockMultipartFile("file", "users.csv", "text/csv", csv.toByteArray())

        val response = controller.importCsv(file)

        assertEquals(HttpStatus.OK, response.statusCode)
        // ensure processing called with parsed users
        verify(processing).process(check {
            assertEquals(2, it.size)
            assertEquals(1L, it[0].id)
            assertEquals("Alice", it[0].name)
        })
    }

    @Test
    fun `importCsv should throw BAD_REQUEST when id header missing`() {
        val csv = "name,email\nAlice,alice@example.com\n"
        val file = MockMultipartFile("file", "users.csv", "text/csv", csv.toByteArray())

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

package com.planet.assessment.adapters.inbound.rest.controller

import com.planet.assessment.adapters.TestUtils.ALT_EMAIL
import com.planet.assessment.adapters.TestUtils.ALT_NAME
import com.planet.assessment.adapters.TestUtils.CONTENT_TYPE
import com.planet.assessment.adapters.TestUtils.CSV_TEXT
import com.planet.assessment.adapters.TestUtils.CSV_TEXT_NO_EMAIL
import com.planet.assessment.adapters.TestUtils.CSV_TEXT_NO_ID
import com.planet.assessment.adapters.TestUtils.DEFAULT_EMAIL
import com.planet.assessment.adapters.TestUtils.DEFAULT_NAME
import com.planet.assessment.adapters.TestUtils.FILE_NAME
import com.planet.assessment.adapters.TestUtils.ORIGINAL_FILE_NAME
import com.planet.assessment.adapters.inbound.rest.controller.mapper.ExportFormatMapper.toMediaType
import com.planet.assessment.application.port.inbound.service.UserProcessingServicePort
import com.planet.assessment.application.port.inbound.service.UserRetrievalServicePort
import com.planet.assessment.user.User
import com.planet.assessment.user.UserDiscardReason
import com.planet.assessment.user.UserValidationResult
import com.planetassessment.adapters.model.ExportFormat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.whenever
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.server.ResponseStatusException
import com.planet.assessment.format.ExportFormat as DomainExportFormat

class UserFileControllerTest {
    private val processing = mock<UserProcessingServicePort>()
    private val retrieval = mock<UserRetrievalServicePort>()
    private val controller = UserFileController(processing, retrieval)

    @Test
    fun `importCsv should parse file and call processing service and return OK`() {
        val csv = CSV_TEXT
        val file = MockMultipartFile(FILE_NAME, ORIGINAL_FILE_NAME, CONTENT_TYPE, csv.toByteArray())
        val userList = listOf(User(1L, DEFAULT_NAME, DEFAULT_EMAIL), User(2L, ALT_NAME, ALT_EMAIL))
        val processResult = Pair(setOf(1L, 2L), emptyList<UserValidationResult>())

        whenever(processing.process(eq(userList))).thenReturn(processResult)
        val response = controller.importCsv(file)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(
            "Users upload successfully by Id: 1 ; 2. No Users discarded. Users that have badly formatted columns are not shown here. ",
            response.body?.message,
        )

        verify(processing).process(
            eq(listOf(User(1L, DEFAULT_NAME, DEFAULT_EMAIL), User(2L, ALT_NAME, ALT_EMAIL))),
        )
    }

    @Test
    fun `importCsv should parse file and return OK even if it discards users`() {
        val csv = CSV_TEXT_NO_EMAIL
        val file = MockMultipartFile(FILE_NAME, ORIGINAL_FILE_NAME, CONTENT_TYPE, csv.toByteArray())
        val userList = listOf(User(1L, DEFAULT_NAME, ""))
        val processResult =
            Pair(
                emptySet<Long>(),
                listOf(
                    UserValidationResult(1L, false, UserDiscardReason.BLANK_EMAIL),
                ),
            )

        whenever(processing.process(eq(userList))).thenReturn(processResult)
        val response = controller.importCsv(file)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(
            "No Users processed. " +
                "Users discarded: " +
                "Id: 1 - Discard Reason: User discarded due to 'email' being blank.",
            response.body?.message,
        )

        verify(processing).process(
            eq(listOf(User(1L, DEFAULT_NAME, ""))),
        )
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

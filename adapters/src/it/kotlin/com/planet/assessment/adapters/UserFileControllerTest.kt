package com.planet.assessment.adapters

import com.planet.assessment.BaseIntegrationTest
import com.planet.assessment.TestUtils.ALT_EMAIL
import com.planet.assessment.TestUtils.ALT_NAME
import com.planet.assessment.TestUtils.CONTENT_TYPE
import com.planet.assessment.TestUtils.CSV_TEXT
import com.planet.assessment.TestUtils.CSV_TEXT_NO_ID
import com.planet.assessment.TestUtils.CSV_WITH_DUPLICATE_COLUMNS
import com.planet.assessment.TestUtils.DEFAULT_EMAIL
import com.planet.assessment.TestUtils.DEFAULT_NAME
import com.planet.assessment.TestUtils.FILE_NAME
import com.planet.assessment.TestUtils.ORIGINAL_FILE_NAME
import com.planet.assessment.TestUtils.PATH_EXPORT_DATA
import com.planet.assessment.TestUtils.PATH_IMPORT_CSV
import com.planet.assessment.TestUtils.buildUser
import com.planet.assessment.application.port.inbound.service.UserProcessingServicePort
import com.planet.assessment.application.port.inbound.service.UserRetrievalServicePort
import com.planet.assessment.column.ExportColumn
import com.planet.assessment.format.ExportFormat as DomainExportFormat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.check
import org.mockito.kotlin.eq
import org.mockito.kotlin.whenever
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
class UserFileControllerTest : BaseIntegrationTest() {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockitoBean
    lateinit var processing: UserProcessingServicePort

    @MockitoBean
    lateinit var retrieval: UserRetrievalServicePort

    @Test
    fun `importCsv should parse file and delegate to processing and return OK`() {
        val file = MockMultipartFile(FILE_NAME, ORIGINAL_FILE_NAME, CONTENT_TYPE, CSV_TEXT.toByteArray())

        mockMvc.perform(multipart(PATH_IMPORT_CSV).file(file))
            .andExpect(status().isOk)

        val expectedUser1 = buildUser(
            id = 1L,
            name = DEFAULT_NAME,
            email = DEFAULT_EMAIL,
            age = null,
            country = null,
            phone = null
        )


        val expectedUser2 = buildUser(
            id = 2L,
            name = ALT_NAME,
            email = ALT_EMAIL,
            age = null,
            country = null,
            phone = null
        )

        verify(processing).process(check { users ->
            assertEquals(expectedUser1, users[0])
            assertEquals(expectedUser2, users[1])
        })
    }

    @Test
    fun `importCsv missing id header returns bad request`() {
        val file = MockMultipartFile(FILE_NAME, ORIGINAL_FILE_NAME, CONTENT_TYPE, CSV_TEXT_NO_ID.toByteArray())

        mockMvc.perform(multipart(PATH_IMPORT_CSV).file(file))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `importCsv duplicate columns returns bad request`() {
        val file = MockMultipartFile(FILE_NAME, ORIGINAL_FILE_NAME, CONTENT_TYPE, CSV_WITH_DUPLICATE_COLUMNS.toByteArray())

        mockMvc.perform(multipart(PATH_IMPORT_CSV).file(file))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `exportData delegates to retrieval and returns resource with content type`() {
        val resource = ByteArrayResource(CSV_TEXT.toByteArray())
        whenever(
            retrieval.retrieveUsers(
                eq(DomainExportFormat.CSV),
                eq(listOf(
                    ExportColumn.ID,
                    ExportColumn.NAME,
                    ExportColumn.EMAIL
                ))
            )
        ).thenReturn(resource)

        mockMvc.perform(
            get(PATH_EXPORT_DATA)
                .param("format", "csv")
                .param("columns", "id,name,email"))
            .andExpect(status().isOk)
            .andExpect { result ->
                val contentType = result.response.getHeader("Content-Type")!!
                assertEquals(MediaType.parseMediaType("text/csv").type, MediaType.parseMediaType(contentType).type)
                assertEquals(CSV_TEXT, result.response.contentAsString)
            }
    }
}

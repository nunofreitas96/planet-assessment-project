package com.planet.assessment.adapters.inbound.rest.controller.parser

import com.planet.assessment.adapters.TestUtils.ALT_EMAIL
import com.planet.assessment.adapters.TestUtils.ALT_NAME
import com.planet.assessment.adapters.TestUtils.CONTENT_TYPE
import com.planet.assessment.adapters.TestUtils.CSV_TEXT
import com.planet.assessment.adapters.TestUtils.CSV_TEXT_NO_ID
import com.planet.assessment.adapters.TestUtils.CSV_WITH_DUPLICATE_COLUMNS
import com.planet.assessment.adapters.TestUtils.CSV_WITH_INVALID_ID
import com.planet.assessment.adapters.TestUtils.DEFAULT_EMAIL
import com.planet.assessment.adapters.TestUtils.DEFAULT_NAME
import com.planet.assessment.adapters.TestUtils.FILE_NAME
import com.planet.assessment.adapters.TestUtils.ORIGINAL_FILE_NAME
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.server.ResponseStatusException

class UserParserTest {
    @Test
    fun `parseUsers should parse valid CSV into users`() {
        val file = MockMultipartFile(FILE_NAME, ORIGINAL_FILE_NAME, CONTENT_TYPE, CSV_TEXT.toByteArray())

        val users = UserParser.parseUsers(file)

        assertEquals(2, users.size)
        assertEquals(1L, users[0].id)
        assertEquals(DEFAULT_NAME, users[0].name)
        assertEquals(DEFAULT_EMAIL, users[0].email)

        assertEquals(2L, users[1].id)
        assertEquals(ALT_NAME, users[1].name)
        assertEquals(ALT_EMAIL, users[1].email)
    }

    @Test
    fun `parseUsers should throw BAD_REQUEST when id header missing`() {
        val file = MockMultipartFile(FILE_NAME, ORIGINAL_FILE_NAME, CONTENT_TYPE, CSV_TEXT_NO_ID.toByteArray())

        assertThrows<ResponseStatusException> {
            UserParser.parseUsers(file)
        }
    }

    @Test
    fun `parseUsers should throw BAD_REQUEST when duplicate columns present`() {
        val file = MockMultipartFile(FILE_NAME, ORIGINAL_FILE_NAME, CONTENT_TYPE, CSV_WITH_DUPLICATE_COLUMNS.toByteArray())

        assertThrows<ResponseStatusException> {
            UserParser.parseUsers(file)
        }
    }

    @Test
    fun `parseUsers should skip records with invalid id and parse others`() {
        val file = MockMultipartFile(FILE_NAME, ORIGINAL_FILE_NAME, CONTENT_TYPE, CSV_WITH_INVALID_ID.toByteArray())

        val users = UserParser.parseUsers(file)

        assertEquals(1, users.size)
        assertEquals(2L, users[0].id)
        assertEquals("Bob", users[0].name)
    }
}

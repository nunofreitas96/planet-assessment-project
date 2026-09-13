package com.planet.assessment.application.formatter

import com.planet.assessment.application.TestUtils
import com.planet.assessment.application.TestUtils.ALT_CSV_LINE
import com.planet.assessment.application.TestUtils.ALT_EMAIL
import com.planet.assessment.application.TestUtils.ALT_NAME
import com.planet.assessment.application.TestUtils.DEFAULT_CSV_LINE
import com.planet.assessment.application.TestUtils.DEFAULT_EMAIL
import com.planet.assessment.application.TestUtils.DEFAULT_NAME
import com.planet.assessment.application.TestUtils.EMAIL_COLUMN
import com.planet.assessment.application.TestUtils.ID_COLUMN
import com.planet.assessment.application.TestUtils.NAME_COLUMN
import com.planet.assessment.application.TestUtils.buildUser
import com.planet.assessment.column.ExportColumn
import com.planet.assessment.user.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.core.io.Resource

class TxtUserFormatterTest {

    private val formatter = TxtUserFormatter()

    @Test
    fun `format should produce plain text with header and rows`() {
        val columns = listOf(ExportColumn.ID, ExportColumn.NAME, ExportColumn.EMAIL)
        val users = listOf(
            buildUser(id = 1L, name = DEFAULT_NAME, email = DEFAULT_EMAIL),
            buildUser(id = 2L, name = ALT_NAME, email = ALT_EMAIL)
        )

        val resource: Resource = formatter.format(users, columns)
        val text = resource.inputStream.bufferedReader(Charsets.UTF_8).use { it.readText() }

        val lines = text.lines().filter { it.isNotBlank() }
        assertTrue(lines[0].contains(ID_COLUMN))
        assertTrue(lines[0].contains(NAME_COLUMN))
        assertTrue(lines[0].contains(EMAIL_COLUMN))

        assertTrue(lines.contains(DEFAULT_CSV_LINE))
        assertTrue(lines.contains(ALT_CSV_LINE))
    }

    @Test
    fun `format with empty users should contain only header line`() {
        val columns = listOf(ExportColumn.ID, ExportColumn.NAME)
        val users = emptyList<User>()

        val resource = formatter.format(users, columns)
        val text = resource.inputStream.bufferedReader(Charsets.UTF_8).use { it.readText() }

        val lines = text.lines().filter { it.isNotBlank() }
        assertEquals(1, lines.size)
        assertEquals("ID,NAME", lines[0])
    }
}

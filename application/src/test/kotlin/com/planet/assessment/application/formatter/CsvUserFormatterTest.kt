package com.planet.assessment.application.formatter

import com.planet.assessment.application.TestUtils
import com.planet.assessment.application.TestUtils.ALT_CSV_LINE
import com.planet.assessment.application.TestUtils.ALT_EMAIL
import com.planet.assessment.application.TestUtils.ALT_NAME
import com.planet.assessment.application.TestUtils.DEFAULT_CSV_LINE
import com.planet.assessment.application.TestUtils.DEFAULT_EMAIL
import com.planet.assessment.application.TestUtils.DEFAULT_NAME
import com.planet.assessment.application.TestUtils.buildUser
import com.planet.assessment.column.ExportColumn
import com.planet.assessment.user.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.core.io.Resource

class CsvUserFormatterTest {

    private val formatter = CsvUserFormatter()

    @Test
    fun `format should produce CSV with header and rows for users`() {
        val columns = listOf(ExportColumn.ID, ExportColumn.NAME, ExportColumn.EMAIL)
        val users = listOf(
            buildUser(id = 1L, name = DEFAULT_NAME, email = DEFAULT_EMAIL),
            buildUser(id = 2L, name = ALT_NAME, email = ALT_EMAIL)
        )

        val resource: Resource = formatter.format(users, columns)
        val text = resource.inputStream.bufferedReader(Charsets.UTF_8).use { it.readText() }

        val expectedHeader = columns.joinToString(",") { it.name }
        assertTrue(text.contains(expectedHeader))

        assertTrue(text.contains(DEFAULT_CSV_LINE))
        assertTrue(text.contains(ALT_CSV_LINE))
    }

    @Test
    fun `format with empty users list should still contain header only`() {
        val columns = listOf(ExportColumn.ID, ExportColumn.NAME)
        val users = emptyList<User>()

        val resource = formatter.format(users, columns)
        val text = resource.inputStream.bufferedReader(Charsets.UTF_8).use { it.readText() }

        val expectedHeader = columns.joinToString(",") { it.name }
        assertTrue(text.contains(expectedHeader))

        val lines = text.lines().filter { it.isNotBlank() }
        assertEquals(1, lines.size)
    }
}

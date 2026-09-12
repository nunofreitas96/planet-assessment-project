package com.planet.assessment.application.formatter

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
            User(id = 1L, name = "Alice", email = "alice@example.com"),
            User(id = 2L, name = "Bob", email = "bob@example.com")
        )

        val resource: Resource = formatter.format(users, columns)
        val text = resource.inputStream.bufferedReader(Charsets.UTF_8).use { it.readText() }

        val lines = text.lines().filter { it.isNotBlank() }
        assertTrue(lines[0].contains("ID"))
        assertTrue(lines[0].contains("NAME"))
        assertTrue(lines[0].contains("EMAIL"))

        assertTrue(lines.contains("1,Alice,alice@example.com"))
        assertTrue(lines.contains("2,Bob,bob@example.com"))
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

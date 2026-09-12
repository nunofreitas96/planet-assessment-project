package com.planet.assessment.application.formatter

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
            User(id = 1L, name = "Alice", email = "alice@example.com"),
            User(id = 2L, name = "Bob", email = "bob@example.com")
        )

        val resource: Resource = formatter.format(users, columns)
        val text = resource.inputStream.bufferedReader(Charsets.UTF_8).use { it.readText() }

        val expectedHeader = columns.joinToString(",") { it.name }
        assertTrue(text.contains(expectedHeader), "CSV should contain header: $expectedHeader")

        // each user row should appear with values in the same order
        assertTrue(text.contains("1,Alice,alice@example.com"), "CSV should contain first user row")
        assertTrue(text.contains("2,Bob,bob@example.com"), "CSV should contain second user row")
    }

    @Test
    fun `format with empty users list should still contain header only`() {
        val columns = listOf(ExportColumn.ID, ExportColumn.NAME)
        val users = emptyList<User>()

        val resource = formatter.format(users, columns)
        val text = resource.inputStream.bufferedReader(Charsets.UTF_8).use { it.readText() }

        val expectedHeader = columns.joinToString(",") { it.name }
        assertTrue(text.contains(expectedHeader), "CSV should contain header even when no users present")

        // No user rows expected
        // Removing header line and trimming should leave empty or whitespace only
        val lines = text.lines().filter { it.isNotBlank() }
        // header exists, so lines size should be 1
        assertEquals(1, lines.size, "CSV with no users should only have header line")
    }
}

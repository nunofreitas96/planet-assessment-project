package com.planet.assessment.application.service

import com.planet.assessment.application.port.inbound.service.UserFormatterPort
import com.planet.assessment.application.port.outbound.persistence.UserPersistencePort
import com.planet.assessment.column.ExportColumn
import com.planet.assessment.format.ExportFormat
import com.planet.assessment.user.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.*
import org.springframework.core.io.ByteArrayResource

class UserRetrievalServiceTest {

    private val persistence = mock<UserPersistencePort>()

    @Test
    fun `retrieveUsers delegates to correct formatter based on format`() {
        val users = listOf(User(id = 1L, name = "A"))
        whenever(persistence.findAll()).thenReturn(users)

        val csvFormatter = mock<UserFormatterPort>()
        whenever(csvFormatter.format).thenReturn(ExportFormat.CSV)
        val expectedResource = ByteArrayResource("csv".toByteArray())
        whenever(csvFormatter.format(users, listOf(ExportColumn.ID))).thenReturn(expectedResource)

        val xlsxFormatter = mock<UserFormatterPort>()
        whenever(xlsxFormatter.format).thenReturn(ExportFormat.XLSX)

        val service = UserRetrievalService(listOf(csvFormatter, xlsxFormatter), persistence)

        val result = service.retrieveUsers(ExportFormat.CSV, listOf(ExportColumn.ID))

        assertSame(expectedResource, result)
        verify(persistence).findAll()
        verify(csvFormatter).format(users, listOf(ExportColumn.ID))
        verify(xlsxFormatter, never()).format(any(), any())
    }

    @Test
    fun `retrieveUsers throws when no formatter available for requested format`() {
        val users = listOf(User(id = 2L))
        whenever(persistence.findAll()).thenReturn(users)

        val service = UserRetrievalService(emptyList(), persistence)

        assertThrows<NullPointerException>{
            service.retrieveUsers(ExportFormat.CSV, listOf(ExportColumn.ID))
        }
    }
}

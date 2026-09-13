package com.planet.assessment.application.formatter

import com.planet.assessment.application.TestUtils.DEFAULT_NAME
import com.planet.assessment.application.TestUtils.buildUser
import com.planet.assessment.application.port.inbound.service.ExcelTypeUserFormatterPort
import com.planet.assessment.column.ExportColumn
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.ss.usermodel.Workbook
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.core.io.ByteArrayResource
import kotlin.test.assertTrue

class XlsxUserFormatterTest {

    private val delegate = mock<ExcelTypeUserFormatterPort>()
    private val formatter = XlsxUserFormatter(delegate)

    @Test
    fun `format should delegate to ExcelTypeUserFormatter with XSSFWorkbook and return resource`() {
        val columns = listOf(ExportColumn.ID, ExportColumn.NAME)
        val users = listOf(buildUser(id = 1L, name = DEFAULT_NAME))

        val expected = ByteArrayResource("xlsx".toByteArray())
        whenever(delegate.format(any(), any(), any())).thenReturn(expected)

        val result = formatter.format(users, columns)

        assertEquals(expected, result)

        val wbCaptor = argumentCaptor<Workbook>()
        verify(delegate).format(wbCaptor.capture(), eq(users), eq(columns))
        assertTrue(wbCaptor.firstValue is XSSFWorkbook)
    }
}

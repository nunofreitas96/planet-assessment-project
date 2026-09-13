package com.planet.assessment.application.formatter

import com.planet.assessment.application.TestUtils
import com.planet.assessment.application.TestUtils.DEFAULT_NAME
import com.planet.assessment.application.TestUtils.buildUser
import com.planet.assessment.application.port.inbound.service.ExcelTypeUserFormatterPort
import com.planet.assessment.column.ExportColumn
import com.planet.assessment.user.User
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Workbook
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.core.io.ByteArrayResource
import org.mockito.kotlin.*

class XlsUserFormatterTest {

    private val delegate = mock<ExcelTypeUserFormatterPort>()
    private val formatter = XlsUserFormatter(delegate)

    @Test
    fun `format should delegate to ExcelTypeUserFormatter with HSSFWorkbook and return resource`() {
        val columns = listOf(ExportColumn.ID, ExportColumn.NAME)
        val users = listOf(buildUser(id = 1L, name = DEFAULT_NAME))

        val expected = ByteArrayResource("xls".toByteArray())
        whenever(delegate.format(any(), any(), any())).thenReturn(expected)

        val result = formatter.format(users, columns)

        assertSame(expected, result)

        val wbCaptor = argumentCaptor<Workbook>()
        verify(delegate).format(wbCaptor.capture(), eq(users), eq(columns))
        assertTrue(wbCaptor.firstValue is HSSFWorkbook)
    }
}

package com.planet.assessment.application.formatter

import com.planet.assessment.application.TestUtils.ALT_EMAIL
import com.planet.assessment.application.TestUtils.ALT_NAME
import com.planet.assessment.application.TestUtils.DEFAULT_EMAIL
import com.planet.assessment.application.TestUtils.DEFAULT_NAME
import com.planet.assessment.application.TestUtils.EMAIL_COLUMN
import com.planet.assessment.application.TestUtils.ID_COLUMN
import com.planet.assessment.application.TestUtils.NAME_COLUMN
import com.planet.assessment.application.TestUtils.buildUser
import com.planet.assessment.column.ExportColumn
import com.planet.assessment.user.User
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.core.io.Resource
import java.io.ByteArrayInputStream

class ExcelTypeUserFormatterTest {
    private val formatter = ExcelTypeUserFormatter()

    @Test
    fun `format should produce XLSX with header and rows for users`() {
        val columns = listOf(ExportColumn.ID, ExportColumn.NAME, ExportColumn.EMAIL)
        val users =
            listOf(
                buildUser(id = 1L, name = DEFAULT_NAME, email = DEFAULT_EMAIL),
                buildUser(id = 2L, name = ALT_NAME, email = ALT_EMAIL),
            )

        val workbook = XSSFWorkbook()
        val resource: Resource = formatter.format(workbook, users, columns)

        val produced = ByteArrayInputStream(resource.contentAsByteArray)
        val wb = WorkbookFactory.create(produced)
        val sheet = wb.getSheetAt(0)
        val formatterUtil = DataFormatter()

        val headerRow = sheet.getRow(0)
        assertEquals(ID_COLUMN, formatterUtil.formatCellValue(headerRow.getCell(0)))
        assertEquals(NAME_COLUMN, formatterUtil.formatCellValue(headerRow.getCell(1)))
        assertEquals(EMAIL_COLUMN, formatterUtil.formatCellValue(headerRow.getCell(2)))

        val row1 = sheet.getRow(1)
        assertEquals("1", formatterUtil.formatCellValue(row1.getCell(0)))
        assertEquals(DEFAULT_NAME, formatterUtil.formatCellValue(row1.getCell(1)))
        assertEquals(DEFAULT_EMAIL, formatterUtil.formatCellValue(row1.getCell(2)))

        val row2 = sheet.getRow(2)
        assertEquals("2", formatterUtil.formatCellValue(row2.getCell(0)))
        assertEquals(ALT_NAME, formatterUtil.formatCellValue(row2.getCell(1)))
        assertEquals(ALT_EMAIL, formatterUtil.formatCellValue(row2.getCell(2)))

        wb.close()
    }

    @Test
    fun `format with empty users should produce workbook with header only and blank cells for rows`() {
        val columns = listOf(ExportColumn.ID, ExportColumn.NAME)
        val users = emptyList<User>()

        val workbook = XSSFWorkbook()
        val resource = formatter.format(workbook, users, columns)

        val produced = ByteArrayInputStream(resource.contentAsByteArray)
        val wb = WorkbookFactory.create(produced)
        val sheet = wb.getSheetAt(0)
        val formatterUtil = DataFormatter()

        val headerRow = sheet.getRow(0)
        assertEquals(ID_COLUMN, formatterUtil.formatCellValue(headerRow.getCell(0)))
        assertEquals(NAME_COLUMN, formatterUtil.formatCellValue(headerRow.getCell(1)))

        assertNull(sheet.getRow(1))

        wb.close()
    }

    @Test
    fun `format with HSSFWorkbook should produce XLS with header and rows for users`() {
        val columns = listOf(ExportColumn.ID, ExportColumn.NAME, ExportColumn.EMAIL)
        val users =
            listOf(
                buildUser(id = 1L, name = DEFAULT_NAME, email = DEFAULT_EMAIL),
                buildUser(id = 2L, name = ALT_NAME, email = ALT_EMAIL),
            )

        val workbook = HSSFWorkbook()
        val resource = formatter.format(workbook, users, columns)

        val produced = ByteArrayInputStream(resource.contentAsByteArray)
        val wb = WorkbookFactory.create(produced)
        val sheet = wb.getSheetAt(0)
        val formatterUtil = DataFormatter()

        val headerRow = sheet.getRow(0)
        assertEquals(ID_COLUMN, formatterUtil.formatCellValue(headerRow.getCell(0)))
        assertEquals(NAME_COLUMN, formatterUtil.formatCellValue(headerRow.getCell(1)))
        assertEquals(EMAIL_COLUMN, formatterUtil.formatCellValue(headerRow.getCell(2)))

        val row1 = sheet.getRow(1)
        assertEquals("1", formatterUtil.formatCellValue(row1.getCell(0)))
        assertEquals(DEFAULT_NAME, formatterUtil.formatCellValue(row1.getCell(1)))
        assertEquals(DEFAULT_EMAIL, formatterUtil.formatCellValue(row1.getCell(2)))

        val row2 = sheet.getRow(2)
        assertEquals("2", formatterUtil.formatCellValue(row2.getCell(0)))
        assertEquals(ALT_NAME, formatterUtil.formatCellValue(row2.getCell(1)))
        assertEquals(ALT_EMAIL, formatterUtil.formatCellValue(row2.getCell(2)))

        wb.close()
    }
}

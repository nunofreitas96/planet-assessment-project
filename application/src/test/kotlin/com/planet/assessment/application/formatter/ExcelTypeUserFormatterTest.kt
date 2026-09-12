package com.planet.assessment.application.formatter

import com.planet.assessment.column.ExportColumn
import com.planet.assessment.user.User
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.core.io.Resource
import java.io.ByteArrayInputStream

class ExcelTypeUserFormatterTest {

    private val formatter = ExcelTypeUserFormatter()

    @Test
    fun `format should produce XLSX with header and rows for users`() {
        val columns = listOf(ExportColumn.ID, ExportColumn.NAME, ExportColumn.EMAIL)
        val users = listOf(
            User(id = 1L, name = "Alice", email = "alice@example.com"),
            User(id = 2L, name = "Bob", email = "bob@example.com")
        )

        val workbook = XSSFWorkbook()
        val resource: Resource = formatter.format(workbook, users, columns)

        // Read back the produced workbook bytes
        val produced = ByteArrayInputStream(resource.contentAsByteArray)
        val wb = WorkbookFactory.create(produced)
        val sheet = wb.getSheetAt(0)
        val formatterUtil = DataFormatter()

        // header
        val headerRow = sheet.getRow(0)
        assertEquals("ID", formatterUtil.formatCellValue(headerRow.getCell(0)))
        assertEquals("NAME", formatterUtil.formatCellValue(headerRow.getCell(1)))
        assertEquals("EMAIL", formatterUtil.formatCellValue(headerRow.getCell(2)))

        // first data row
        val row1 = sheet.getRow(1)
        assertEquals("1", formatterUtil.formatCellValue(row1.getCell(0)))
        assertEquals("Alice", formatterUtil.formatCellValue(row1.getCell(1)))
        assertEquals("alice@example.com", formatterUtil.formatCellValue(row1.getCell(2)))

        // second data row
        val row2 = sheet.getRow(2)
        assertEquals("2", formatterUtil.formatCellValue(row2.getCell(0)))
        assertEquals("Bob", formatterUtil.formatCellValue(row2.getCell(1)))
        assertEquals("bob@example.com", formatterUtil.formatCellValue(row2.getCell(2)))

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
        assertEquals("ID", formatterUtil.formatCellValue(headerRow.getCell(0)))
        assertEquals("NAME", formatterUtil.formatCellValue(headerRow.getCell(1)))

        // ensure there are no data rows (only header)
        assertNull(sheet.getRow(1))

        wb.close()
    }

    @Test
    fun `format with HSSFWorkbook should produce XLS with header and rows for users`() {
        val columns = listOf(ExportColumn.ID, ExportColumn.NAME, ExportColumn.EMAIL)
        val users = listOf(
            User(id = 10L, name = "Carol", email = "carol@example.com"),
            User(id = 11L, name = "Dave", email = "dave@example.com")
        )

        val workbook = HSSFWorkbook()
        val resource = formatter.format(workbook, users, columns)

        val produced = ByteArrayInputStream(resource.contentAsByteArray)
        val wb = WorkbookFactory.create(produced)
        val sheet = wb.getSheetAt(0)
        val formatterUtil = DataFormatter()

        // header
        val headerRow = sheet.getRow(0)
        assertEquals("ID", formatterUtil.formatCellValue(headerRow.getCell(0)))
        assertEquals("NAME", formatterUtil.formatCellValue(headerRow.getCell(1)))
        assertEquals("EMAIL", formatterUtil.formatCellValue(headerRow.getCell(2)))

        // first data row
        val row1 = sheet.getRow(1)
        assertEquals("10", formatterUtil.formatCellValue(row1.getCell(0)))
        assertEquals("Carol", formatterUtil.formatCellValue(row1.getCell(1)))
        assertEquals("carol@example.com", formatterUtil.formatCellValue(row1.getCell(2)))

        // second data row
        val row2 = sheet.getRow(2)
        assertEquals("11", formatterUtil.formatCellValue(row2.getCell(0)))
        assertEquals("Dave", formatterUtil.formatCellValue(row2.getCell(1)))
        assertEquals("dave@example.com", formatterUtil.formatCellValue(row2.getCell(2)))

        wb.close()
    }
}

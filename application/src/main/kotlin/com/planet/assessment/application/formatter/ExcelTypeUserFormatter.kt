package com.planet.assessment.application.formatter

import com.planet.assessment.application.mapper.UserValueMapper.valueOf
import com.planet.assessment.application.port.inbound.service.ExcelTypeUserFormatterPort
import com.planet.assessment.column.ExportColumn
import com.planet.assessment.user.User
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Workbook
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import java.io.ByteArrayOutputStream
import kotlin.collections.forEachIndexed

@Component
class ExcelTypeUserFormatter : ExcelTypeUserFormatterPort {

    override fun format(
        workbook: Workbook,
        users: List<User>,
        columns: List<ExportColumn>
    ): Resource {
        workbook.use {
            val sheet = workbook.createSheet("Users")

            val header = sheet.createRow(0)

            columns.forEachIndexed { index, column ->
                header.createCell(index).setCellValue(column.name)
            }

            users.forEachIndexed { rowIndex, user ->
                val row = sheet.createRow(rowIndex + 1)

                columns.forEachIndexed { columnIndex, column ->
                    setCellValue(
                        row.createCell(columnIndex),
                        user.valueOf(column)
                    )
                }
            }

            val output = ByteArrayOutputStream()
            workbook.write(output)

            return ByteArrayResource(output.toByteArray())
        }
    }

    private fun setCellValue(
        cell: Cell,
        value: Any?
    ) {
        when (value) {
            null -> cell.setBlank()
            is Number -> cell.setCellValue(value.toDouble())
            else -> cell.setCellValue(value.toString())
        }
    }
}
package com.planet.assessment.application.service

import com.planet.assessment.application.port.inbound.service.ExcelTypeUserFormatterPort
import com.planet.assessment.application.port.inbound.service.UserFormatterPort
import com.planet.assessment.column.ExportColumn
import com.planet.assessment.format.ExportFormat.XLSX
import com.planet.assessment.user.User
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

@Component
class XlsxUserFormatter(
    private val excelTypeUserFormatter: ExcelTypeUserFormatterPort
) : UserFormatterPort {

    override val format = XLSX
    override fun format(
        users: List<User>,
        columns: List<ExportColumn>
    ): Resource {
        val workbook = XSSFWorkbook()

        return excelTypeUserFormatter.format(workbook, users, columns)
    }
}
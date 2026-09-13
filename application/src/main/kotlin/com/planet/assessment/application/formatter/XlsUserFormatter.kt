package com.planet.assessment.application.formatter

import com.planet.assessment.application.port.inbound.service.ExcelTypeUserFormatterPort
import com.planet.assessment.application.port.inbound.service.UserFormatterPort
import com.planet.assessment.column.ExportColumn
import com.planet.assessment.format.ExportFormat.XLS
import com.planet.assessment.user.User
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

@Component
class XlsUserFormatter(
    private val excelTypeUserFormatter: ExcelTypeUserFormatterPort
) : UserFormatterPort {

    override val format = XLS
    override fun format(
        users: List<User>,
        columns: List<ExportColumn>
    ): Resource {
        val workbook = HSSFWorkbook()

        return excelTypeUserFormatter.format(workbook, users, columns)
    }
}
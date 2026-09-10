package com.planet.assessment.application.port.inbound.service

import com.planet.assessment.column.ExportColumn
import com.planet.assessment.user.User
import org.apache.poi.ss.usermodel.Workbook
import org.springframework.core.io.Resource

interface ExcelTypeUserFormatterPort {
    fun format(
        workbook: Workbook,
        users: List<User>,
        columns: List<ExportColumn>
    ): Resource
}
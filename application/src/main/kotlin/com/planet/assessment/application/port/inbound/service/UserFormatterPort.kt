package com.planet.assessment.application.port.inbound.service

import com.planet.assessment.column.ExportColumn
import com.planet.assessment.format.ExportFormat
import com.planet.assessment.user.User
import org.springframework.core.io.Resource

interface UserFormatterPort {
    val format: ExportFormat

    fun format(
        users: List<User>,
        columns: List<ExportColumn>,
    ): Resource
}

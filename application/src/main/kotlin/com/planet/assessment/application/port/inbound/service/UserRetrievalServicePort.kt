package com.planet.assessment.application.port.inbound.service

import com.planet.assessment.column.ExportColumn
import com.planet.assessment.format.ExportFormat
import org.springframework.core.io.Resource

interface UserRetrievalServicePort {
    fun retrieveUsers(
        format: ExportFormat,
        columns: List<ExportColumn>,
    ): Resource
}

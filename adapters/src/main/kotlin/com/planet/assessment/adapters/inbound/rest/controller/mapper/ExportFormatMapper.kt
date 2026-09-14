package com.planet.assessment.adapters.inbound.rest.controller.mapper

import com.planetassessment.adapters.model.ExportFormat
import org.springframework.http.MediaType
import com.planet.assessment.format.ExportFormat as DomainExportFormat

object ExportFormatMapper {
    fun ExportFormat.toDomainExportFormat(): DomainExportFormat =
        when (this) {
            ExportFormat.CSV -> DomainExportFormat.CSV
            ExportFormat.TXT -> DomainExportFormat.TXT
            ExportFormat.XLS -> DomainExportFormat.XLS
            ExportFormat.XLSX -> DomainExportFormat.XLSX
        }

    fun ExportFormat.toMediaType(): MediaType =
        when (this) {
            ExportFormat.CSV -> MediaType.parseMediaType("text/csv")
            ExportFormat.TXT -> MediaType.TEXT_PLAIN
            ExportFormat.XLS -> MediaType.parseMediaType("application/vnd.ms-excel")
            ExportFormat.XLSX -> MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
        }
}

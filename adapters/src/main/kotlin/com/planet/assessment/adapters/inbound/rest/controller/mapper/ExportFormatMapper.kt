package com.planet.assessment.adapters.inbound.rest.controller.mapper

import com.planetassessment.adapters.model.ExportFormat
import org.springframework.http.MediaType
import com.planet.assessment.format.ExportFormat as DomainExportFormat

object ExportFormatMapper {
    fun ExportFormat.toDomainExportFormat(): DomainExportFormat =
        when (this) {
            ExportFormat.csv -> DomainExportFormat.CSV
            ExportFormat.txt -> DomainExportFormat.TXT
            ExportFormat.xls -> DomainExportFormat.XLS
            ExportFormat.xlsx -> DomainExportFormat.XLSX
        }

    fun ExportFormat.toMediaType(): MediaType =
        when (this) {
            ExportFormat.csv -> MediaType.parseMediaType("text/csv")
            ExportFormat.txt -> MediaType.TEXT_PLAIN
            ExportFormat.xls -> MediaType.parseMediaType("application/vnd.ms-excel")
            ExportFormat.xlsx -> MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
        }
}

package com.planet.assessment.application.formatter

import com.planet.assessment.application.mapper.UserValueMapper.valueOf
import com.planet.assessment.application.port.inbound.service.UserFormatterPort
import com.planet.assessment.column.ExportColumn
import com.planet.assessment.format.ExportFormat.CSV
import com.planet.assessment.user.User
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter

@Component
class CsvUserFormatter : UserFormatterPort {
    override val format = CSV

    override fun format(users: List<User>, columns: List<ExportColumn>): Resource {
        val output = ByteArrayOutputStream()

        val columnsString = columns.map { it.name }.toTypedArray()
        OutputStreamWriter(output, Charsets.UTF_8).use { writer ->
            CSVPrinter(
                writer,
                CSVFormat.DEFAULT
                    .builder()
                    .setHeader(*columnsString)
                    .get()
            ).use { printer ->
                users.forEach { user ->
                    printer.printRecord(
                        columns.map { user.valueOf(it) }
                    )
                }
            }
        }

        return ByteArrayResource(output.toByteArray())
    }
}
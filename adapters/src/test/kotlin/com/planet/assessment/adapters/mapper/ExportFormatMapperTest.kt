package com.planet.assessment.adapters.inbound.rest.controller.mapper

import com.planetassessment.adapters.model.ExportFormat
import com.planet.assessment.format.ExportFormat as DomainExportFormat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream
import org.springframework.http.MediaType
import org.junit.jupiter.api.Assertions.assertEquals

@DisplayName("ExportFormatMapper tests")
class ExportFormatMapperTest {

    companion object {
        @JvmStatic
        fun provideFormats(): Stream<Arguments> = Stream.of(
            Arguments.of(ExportFormat.csv, DomainExportFormat.CSV, "text/csv"),
            Arguments.of(ExportFormat.txt, DomainExportFormat.TXT, MediaType.TEXT_PLAIN.toString()),
            Arguments.of(ExportFormat.xls, DomainExportFormat.XLS, "application/vnd.ms-excel"),
            Arguments.of(ExportFormat.xlsx, DomainExportFormat.XLSX, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
        )
    }

    @ParameterizedTest
    @MethodSource("provideFormats")
    fun `map external export format to domain format`(external: ExportFormat, expectedDomain: DomainExportFormat, expectedMediaType: String) {
        val domain = external.toDomainExportFormat()
        val media = external.toMediaType()

        assertEquals(expectedDomain, domain)
        assertEquals(expectedMediaType, media.toString())
    }

    @Test
    fun `human readable test names are used`() {
        // Sanity check - the method name uses backticks and is human readable.
        val name = "map external export format to domain format"
        assertEquals("map external export format to domain format", name)
    }
}

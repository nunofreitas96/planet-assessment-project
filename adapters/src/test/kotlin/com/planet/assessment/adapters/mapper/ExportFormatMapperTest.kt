package com.planet.assessment.adapters.inbound.rest.controller.mapper

import com.planet.assessment.adapters.inbound.rest.controller.mapper.ExportFormatMapper.toDomainExportFormat
import com.planet.assessment.adapters.inbound.rest.controller.mapper.ExportFormatMapper.toMediaType
import com.planetassessment.adapters.model.ExportFormat
import com.planet.assessment.format.ExportFormat as DomainExportFormat
import org.junit.jupiter.api.DisplayName
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
        fun provideDomainFormats(): Stream<Arguments> = Stream.of(
            Arguments.of(ExportFormat.csv, DomainExportFormat.CSV),
            Arguments.of(ExportFormat.txt, DomainExportFormat.TXT),
            Arguments.of(ExportFormat.xls, DomainExportFormat.XLS),
            Arguments.of(ExportFormat.xlsx, DomainExportFormat.XLSX)
        )

        @JvmStatic
        fun provideMediaTypes(): Stream<Arguments> = Stream.of(
            Arguments.of(ExportFormat.csv, "text/csv"),
            Arguments.of(ExportFormat.txt, MediaType.TEXT_PLAIN.toString()),
            Arguments.of(ExportFormat.xls, "application/vnd.ms-excel"),
            Arguments.of(ExportFormat.xlsx, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
        )
    }

    @ParameterizedTest
    @MethodSource("provideDomainFormats")
    fun `toDomainExportFormat maps external format to domain`(external: ExportFormat, expectedDomain: DomainExportFormat) {
        val domain = external.toDomainExportFormat()
        assertEquals(expectedDomain, domain)
    }

    @ParameterizedTest
    @MethodSource("provideMediaTypes")
    fun `toMediaType maps external format to media type`(external: ExportFormat, expectedMediaType: String) {
        val media = external.toMediaType()
        assertEquals(expectedMediaType, media.toString())
    }
}

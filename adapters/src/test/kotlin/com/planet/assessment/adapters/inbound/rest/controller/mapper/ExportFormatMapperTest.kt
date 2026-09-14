package com.planet.assessment.adapters.inbound.rest.controller.mapper

import com.planet.assessment.adapters.inbound.rest.controller.mapper.ExportFormatMapper.toDomainExportFormat
import com.planet.assessment.adapters.inbound.rest.controller.mapper.ExportFormatMapper.toMediaType
import com.planetassessment.adapters.model.ExportFormat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.http.MediaType
import java.util.stream.Stream
import com.planet.assessment.format.ExportFormat as DomainExportFormat

class ExportFormatMapperTest {
    companion object {
        @JvmStatic
        fun provideDomainFormats(): Stream<Arguments> =
            Stream.of(
                Arguments.of(ExportFormat.CSV, DomainExportFormat.CSV),
                Arguments.of(ExportFormat.TXT, DomainExportFormat.TXT),
                Arguments.of(ExportFormat.XLS, DomainExportFormat.XLS),
                Arguments.of(ExportFormat.XLSX, DomainExportFormat.XLSX),
            )

        @JvmStatic
        fun provideMediaTypes(): Stream<Arguments> =
            Stream.of(
                Arguments.of(ExportFormat.CSV, "text/csv"),
                Arguments.of(ExportFormat.TXT, MediaType.TEXT_PLAIN.toString()),
                Arguments.of(ExportFormat.XLS, "application/vnd.ms-excel"),
                Arguments.of(ExportFormat.XLSX, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
            )
    }

    @ParameterizedTest
    @MethodSource("provideDomainFormats")
    fun `toDomainExportFormat maps external format to domain`(
        external: ExportFormat,
        expectedDomain: DomainExportFormat,
    ) {
        val domain = external.toDomainExportFormat()
        assertEquals(expectedDomain, domain)
    }

    @ParameterizedTest
    @MethodSource("provideMediaTypes")
    fun `toMediaType maps external format to media type`(
        external: ExportFormat,
        expectedMediaType: String,
    ) {
        val media = external.toMediaType()
        assertEquals(expectedMediaType, media.toString())
    }
}

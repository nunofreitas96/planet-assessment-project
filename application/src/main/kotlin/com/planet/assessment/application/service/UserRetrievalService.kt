package com.planet.assessment.application.service

import com.planet.assessment.application.port.inbound.service.UserFormatterPort
import com.planet.assessment.application.port.inbound.service.UserRetrievalServicePort
import com.planet.assessment.application.port.outbound.persistence.UserPersistencePort
import com.planet.assessment.column.ExportColumn
import com.planet.assessment.format.ExportFormat
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class UserRetrievalService (
    private val formatters: List<UserFormatterPort>,
    private val userPersistencePort: UserPersistencePort,
) : UserRetrievalServicePort {

    private val formatterByExportFormat = formatters.associateBy { it.format }

    override fun retrieveUsers(
        format: ExportFormat,
        columns: List<ExportColumn>
    ): Resource {
        val users = userPersistencePort.findAll()

        //TODO - ADD exception handling
        val formatter = formatterByExportFormat[format]!!

        return formatter.format(users, columns)
    }
}
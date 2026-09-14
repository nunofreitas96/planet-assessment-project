package com.planet.assessment.application.formatter

import com.planet.assessment.application.mapper.UserValueMapper.valueOf
import com.planet.assessment.application.port.inbound.service.UserFormatterPort
import com.planet.assessment.column.ExportColumn
import com.planet.assessment.format.ExportFormat.TXT
import com.planet.assessment.user.User
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

@Component
class TxtUserFormatter : UserFormatterPort {
    override val format = TXT

    override fun format(
        users: List<User>,
        columns: List<ExportColumn>,
    ): Resource {
        val content =
            buildString {
                appendLine(columns.joinToString(",") { it.name })

                users.forEach { user ->
                    appendLine(
                        columns.joinToString(",") {
                            user.valueOf(it)?.toString() ?: ""
                        },
                    )
                }
            }

        return ByteArrayResource(content.toByteArray(Charsets.UTF_8))
    }
}

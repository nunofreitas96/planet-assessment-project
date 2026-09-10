package com.planet.assessment.application.mapper

import com.planet.assessment.column.ExportColumn
import com.planet.assessment.user.User

object UserValueMapper {
    fun User.valueOf(column: ExportColumn): Any? =
        when (column) {
            ExportColumn.ID -> id
            ExportColumn.NAME -> name
            ExportColumn.EMAIL -> email
            ExportColumn.AGE -> age
            ExportColumn.COUNTRY -> country
            ExportColumn.PHONE -> phone
        }
}
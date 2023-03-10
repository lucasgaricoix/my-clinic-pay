package br.com.myclinicpay.infra.db.mongoDb.entities

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class UnavailableScheduleEntity(
    @JsonFormat(timezone = "GMT-3")
    val start: LocalDateTime,
    @JsonFormat(timezone = "GMT-3")
    val end: LocalDateTime
)
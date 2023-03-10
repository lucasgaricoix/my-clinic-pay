package br.com.myclinicpay.infra.db.mongoDb.entities

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

class ScheduleEntity(
    @JsonFormat(timezone = "GMT-3")
    val start: LocalDateTime,
    @JsonFormat(timezone = "GMT-3")
    val end: LocalDateTime,
    val duration: Int,
    val patient: PersonEntity,
    val appointmentType: String,
    val description: String?
)

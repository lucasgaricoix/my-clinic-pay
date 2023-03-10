package br.com.myclinicpay.infra.db.mongoDb.entities

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

class ScheduledEntity(
    @JsonFormat(timezone = "GMT-3")
    val at: LocalDateTime,
    val duration: Int,
    val patient: PersonEntity,
    val appointmentType: String,
    val description: String?
)

package br.com.myclinicpay.domain.model.schedule

import br.com.myclinicpay.infra.db.mongoDb.entities.PersonEntity
import java.time.LocalDateTime

class ScheduleDTO(
    val id: String,
    val start: LocalDateTime,
    val end: LocalDateTime,
    val duration: Int,
    val patient: PersonEntity,
    val appointmentType: String,
    val description: String?
)

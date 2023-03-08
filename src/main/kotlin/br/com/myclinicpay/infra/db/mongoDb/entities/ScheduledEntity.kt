package br.com.myclinicpay.infra.db.mongoDb.entities

import br.com.myclinicpay.domain.model.appointment.AppointmentType
import java.time.LocalDateTime

class ScheduledEntity(
    val at: LocalDateTime = LocalDateTime.now(),
    val duration: Int,
    val patient: PersonEntity,
    val type: AppointmentType,
    val description: String?
)
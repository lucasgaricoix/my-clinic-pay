package br.com.myclinicpay.domain.model.schedule

import br.com.myclinicpay.domain.model.person.Person
import java.time.LocalDateTime

class ScheduleDTO(
    val id: String,
    val start: LocalDateTime,
    val end: LocalDateTime,
    val duration: Int,
    val patient: Person,
    val appointmentType: String,
    val description: String?
)

package br.com.myclinicpay.domain.model.appointment

import br.com.myclinicpay.domain.model.schedule.ScheduleDTO
import br.com.myclinicpay.infra.db.mongoDb.entities.UnavailableScheduleEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.UserEntity
import java.time.LocalDate

data class AppointmentDTO(
    val id: String,
    val user: UserEntity,
    val date: LocalDate,
    val schedules: List<ScheduleDTO> = mutableListOf(),
    val unavailableSchedules: MutableList<UnavailableScheduleEntity> = mutableListOf()
)

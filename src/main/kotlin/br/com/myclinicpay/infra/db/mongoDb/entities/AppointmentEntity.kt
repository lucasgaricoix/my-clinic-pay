package br.com.myclinicpay.infra.db.mongoDb.entities

import br.com.myclinicpay.domain.model.appointment.AppointmentDTO
import br.com.myclinicpay.domain.model.schedule.ScheduleDTO
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document("appointment")
class AppointmentEntity(
    @Id
    val id: ObjectId?,
    val userId: String,
    val date: LocalDate,
    val schedules: MutableList<ScheduleEntity> = mutableListOf(),
    val unavailableSchedules: MutableList<UnavailableScheduleEntity> = mutableListOf()
) {
    fun toDTO(user: UserEntity, scheduleDTO: List<ScheduleDTO>): AppointmentDTO {
        return AppointmentDTO(
            this.id.toString(), user, this.date, scheduleDTO, this.unavailableSchedules
        )
    }
}

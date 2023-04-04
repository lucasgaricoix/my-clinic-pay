package br.com.myclinicpay.infra.db.mongoDb.entities

import br.com.myclinicpay.domain.model.appointment.AppointmentDTO
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document("appointment")
class AppointmentEntity(
    @Id val id: ObjectId?,
    val user: UserEntity,
    val date: LocalDate,
    val schedules: MutableList<ScheduleEntity> = mutableListOf(),
    val unavailableSchedules: MutableList<UnavailableScheduleEntity> = mutableListOf()
) {
    fun toDTO(): AppointmentDTO {
        return AppointmentDTO(
            this.id.toString(), this.user, this.date, this.schedules.map { it.toDTO() }, this.unavailableSchedules
        )
    }
}

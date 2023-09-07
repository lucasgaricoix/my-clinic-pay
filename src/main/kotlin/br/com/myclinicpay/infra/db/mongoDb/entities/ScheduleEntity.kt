package br.com.myclinicpay.infra.db.mongoDb.entities

import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.domain.model.schedule.ScheduleDTO
import com.fasterxml.jackson.annotation.JsonFormat
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import java.time.LocalDateTime

class ScheduleEntity(
    @Id
    val id: ObjectId,
    @JsonFormat(timezone = "GMT-3")
    val start: LocalDateTime,
    @JsonFormat(timezone = "GMT-3")
    val end: LocalDateTime,
    val duration: Int,
    val patientId: ObjectId,
    val appointmentType: String,
    val description: String?
) {
    fun toDTO(patient: Person): ScheduleDTO {
        return ScheduleDTO(
            this.id.toString(),
            this.start,
            this.end,
            this.duration,
            patient,
            this.appointmentType,
            this.description
        )
    }
}

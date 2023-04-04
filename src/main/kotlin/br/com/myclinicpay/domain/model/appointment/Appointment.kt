package br.com.myclinicpay.domain.model.appointment

import br.com.myclinicpay.infra.db.mongoDb.entities.*
import org.bson.types.ObjectId
import java.time.LocalDateTime

data class Appointment(
    val patientId: String,
    val userId: String,
    val at: LocalDateTime,
    val duration: Int,
    val appointmentType: AppointmentType,
    val description: String?
) {

    private val timeZoneOffset = 3L
    fun toEntity(personEntity: PersonEntity, userEntity: UserEntity): AppointmentEntity {
        val zonedTime = this.at.minusHours(timeZoneOffset)
        return AppointmentEntity(
            ObjectId.get(), userEntity, zonedTime.toLocalDate(), mutableListOf(
                ScheduleEntity(
                    ObjectId.get(),
                    zonedTime,
                    zonedTime.plusMinutes(this.duration.toLong()),
                    this.duration,
                    personEntity,
                    enumValueOf<AppointmentTypeEntity>(this.appointmentType.name).type,
                    this.description
                )
            ), mutableListOf(
                UnavailableScheduleEntity(
                    zonedTime, zonedTime.plusMinutes(this.duration.toLong())
                )
            )
        )
    }
}

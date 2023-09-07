package br.com.myclinicpay.domain.model.appointment

import br.com.myclinicpay.infra.db.mongoDb.entities.AppointmentEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.AppointmentTypeEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.ScheduleEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.UnavailableScheduleEntity
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
    fun toEntity(): AppointmentEntity {
        val zonedTime = this.at.minusHours(timeZoneOffset)
        return AppointmentEntity(
            ObjectId.get(), this.userId, zonedTime.toLocalDate(), mutableListOf(
                ScheduleEntity(
                    ObjectId.get(),
                    zonedTime,
                    zonedTime.plusMinutes(this.duration.toLong()),
                    this.duration,
                    ObjectId(this.patientId),
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

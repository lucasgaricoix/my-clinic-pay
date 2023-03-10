package br.com.myclinicpay.data.usecases.appointment

import br.com.myclinicpay.infra.db.mongoDb.entities.AppointmentEntity
import org.bson.types.ObjectId
import java.time.LocalDate

interface AppointmentRepository {
    fun create(appointmentEntity: AppointmentEntity): AppointmentEntity

    fun findByDateAndUserId(date: LocalDate, userId: String): AppointmentEntity?

    fun updateScheduledEntityById(id: ObjectId?, appointmentEntity: AppointmentEntity): String
}

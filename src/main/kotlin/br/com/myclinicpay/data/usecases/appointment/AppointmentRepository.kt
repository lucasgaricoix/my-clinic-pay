package br.com.myclinicpay.data.usecases.appointment

import br.com.myclinicpay.infra.db.mongoDb.entities.AppointmentEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.ScheduledEntity
import org.bson.types.ObjectId
import java.time.LocalDate
import java.time.LocalDateTime

interface AppointmentRepository {
    fun create(appointmentEntity: AppointmentEntity): AppointmentEntity

    fun findByDateAndUser(date: LocalDate, name: String): AppointmentEntity?

    fun updateScheduledEntityById(id: ObjectId?, scheduled: MutableList<ScheduledEntity>): String
}
package br.com.myclinicpay.data.usecases.appointment

import br.com.myclinicpay.infra.db.mongoDb.entities.AppointmentEntity
import java.time.LocalDateTime

interface AppointmentRepository {
    fun create(appointmentEntity: AppointmentEntity, at: LocalDateTime): AppointmentEntity
}
package br.com.myclinicpay.domain.usecases.appointment

import br.com.myclinicpay.domain.model.appointment.Appointment
import br.com.myclinicpay.infra.db.mongoDb.entities.AppointmentEntity
import java.time.LocalDateTime

interface AppointmentService {
    fun createOrUpdate(appointment: Appointment): String

    fun findByDateAndUserId(date: LocalDateTime, userId: String): AppointmentEntity
}
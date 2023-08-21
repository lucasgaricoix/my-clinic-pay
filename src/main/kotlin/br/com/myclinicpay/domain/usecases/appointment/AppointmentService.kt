package br.com.myclinicpay.domain.usecases.appointment

import br.com.myclinicpay.domain.model.appointment.Appointment
import br.com.myclinicpay.domain.model.appointment.AppointmentDTO
import java.time.LocalDate
import java.time.LocalDateTime

interface AppointmentService {
    fun createOrUpdate(appointment: Appointment): String

    fun findByDateAndUserId(date: LocalDateTime, userId: String): AppointmentDTO

    fun findWeeklyAppointments(from: LocalDate, to: LocalDate, userId: String): List<AppointmentDTO>

    fun deleteById(id: String, scheduleId: String): String
}

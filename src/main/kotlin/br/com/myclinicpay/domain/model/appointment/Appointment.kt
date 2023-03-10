package br.com.myclinicpay.domain.model.appointment

import java.time.LocalDateTime

data class Appointment(
    val patientId: String,
    val userId: String,
    val at: LocalDateTime,
    val duration: Int,
    val appointmentType: AppointmentType,
    val description: String?
)

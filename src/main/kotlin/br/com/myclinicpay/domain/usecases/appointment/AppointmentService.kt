package br.com.myclinicpay.domain.usecases.appointment

import br.com.myclinicpay.domain.model.appointment.Appointment

interface AppointmentService {
    fun create(appointment: Appointment): String
}
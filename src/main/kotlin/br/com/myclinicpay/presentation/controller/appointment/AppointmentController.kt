package br.com.myclinicpay.presentation.controller.appointment

import br.com.myclinicpay.domain.model.appointment.Appointment
import br.com.myclinicpay.domain.usecases.appointment.AppointmentService
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class AppointmentController(private val appointmentService: AppointmentService) {

    fun createAppointment(request: RequestEntity<Appointment>): ResponseEntity<String> {
        val body = request.body ?: throw Exception("Empty body")
        return ResponseEntity(appointmentService.create(body), HttpStatus.CREATED)
    }

    fun findAppointment() {

    }
}

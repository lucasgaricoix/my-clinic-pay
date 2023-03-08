package br.com.myclinicpay.main.routes

import br.com.myclinicpay.domain.model.appointment.Appointment
import br.com.myclinicpay.presentation.factories.appointment.AppointmentFactory
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/appointments")
class AppointmentRequestMapping {

    @PostMapping
    fun createAppointment(appointmentEntity: RequestEntity<Appointment>): ResponseEntity<String> {
        return AppointmentFactory().buildCreate().createAppointment(appointmentEntity)
    }

    @GetMapping
    fun findAppointment() {

    }
}

package br.com.myclinicpay.main.routes

import br.com.myclinicpay.domain.model.appointment.Appointment
import br.com.myclinicpay.infra.db.mongoDb.entities.AppointmentEntity
import br.com.myclinicpay.presentation.factories.appointment.AppointmentFactory
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/appointments")
class AppointmentRequestMapping {

    @PostMapping
    fun createAppointment(appointmentEntity: RequestEntity<Appointment>): ResponseEntity<String> {
        return AppointmentFactory().build().createAppointment(appointmentEntity)
    }

    @GetMapping("/{userId}")
    fun findAppointmentByUserAndDate(
        @PathVariable userId: String,
        @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(name = "date") date: LocalDate
    ): ResponseEntity<AppointmentEntity> {
        return AppointmentFactory().build().findAppointmentByDateAndUser(date, userId)
    }
}

package br.com.myclinicpay.main.routes

import br.com.myclinicpay.domain.model.appointment.Appointment
import br.com.myclinicpay.domain.model.appointment.AppointmentDTO
import br.com.myclinicpay.presentation.factories.appointment.AppointmentFactory
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/appointments")
class AppointmentRequestMapping {

    @PostMapping
    fun createAppointment(appointment: RequestEntity<Appointment>): ResponseEntity<String> {
        return AppointmentFactory().build().createAppointment(appointment)
    }

    @GetMapping("/{userId}")
    fun findAppointmentByUserAndDate(
        @PathVariable userId: String,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam(name = "date") date: LocalDateTime
    ): ResponseEntity<AppointmentDTO> {
        return AppointmentFactory().build().findAppointmentByDateAndUser(date, userId)
    }

    @GetMapping("/weekly")
    fun findAppointments(
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam(name = "from") from: LocalDateTime,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam(name = "to") to: LocalDateTime,
    ): ResponseEntity<List<AppointmentDTO>> {
        return AppointmentFactory().build().findWeeklyAppointments(from.toLocalDate(), to.toLocalDate())
    }

    @DeleteMapping("/{id}/{scheduleId}")
    fun deleteById(@PathVariable id: String, @PathVariable scheduleId: String): ResponseEntity<String> {
        return AppointmentFactory().build().deleteById(id, scheduleId)
    }
}

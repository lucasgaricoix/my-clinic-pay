package br.com.myclinicpay.main.routes

import br.com.myclinicpay.domain.model.appointment.Appointment
import br.com.myclinicpay.domain.model.appointment.AppointmentDTO
import br.com.myclinicpay.presentation.controller.appointment.AppointmentController
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/appointments")
class AppointmentRequestMapping(val appointmentController: AppointmentController) {

    @PostMapping
    fun createAppointment(appointment: RequestEntity<Appointment>): ResponseEntity<String> {
        return appointmentController.createAppointment(appointment)
    }

    @GetMapping("/{userId}")
    fun findAppointmentByUserAndDate(
        @PathVariable userId: String,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam(name = "date") date: LocalDateTime
    ): ResponseEntity<AppointmentDTO> {
        return appointmentController.findAppointmentByDateAndUser(date, userId)
    }

    @GetMapping("/{userId}/weekly")
    fun findAppointments(
        @PathVariable userId: String,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam(name = "from") from: LocalDateTime,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam(name = "to") to: LocalDateTime,
    ): ResponseEntity<List<AppointmentDTO>> {
        return appointmentController.findWeeklyAppointments(from.toLocalDate(), to.toLocalDate(), userId)
    }

    @DeleteMapping("/{id}/{scheduleId}")
    fun deleteById(@PathVariable id: String, @PathVariable scheduleId: String): ResponseEntity<String> {
        return appointmentController.deleteById(id, scheduleId)
    }
}

package br.com.myclinicpay.presentation.controller.appointment

import br.com.myclinicpay.domain.model.appointment.Appointment
import br.com.myclinicpay.domain.model.appointment.AppointmentDTO
import br.com.myclinicpay.domain.usecases.appointment.AppointmentService
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import java.time.LocalDate
import java.time.LocalDateTime

@Controller
class AppointmentController(private val appointmentService: AppointmentService) {

    fun createAppointment(request: RequestEntity<Appointment>): ResponseEntity<String> {
        val body = request.body ?: throw Exception("Empty body")
        return ResponseEntity(appointmentService.createOrUpdate(body), HttpStatus.CREATED)
    }

    fun findAppointmentByDateAndUser(date: LocalDateTime, userId: String): ResponseEntity<AppointmentDTO> {
        return ResponseEntity(appointmentService.findByDateAndUserId(date, userId), HttpStatus.OK)
    }

    fun findWeeklyAppointments(from: LocalDate, to: LocalDate): ResponseEntity<List<AppointmentDTO>> {
        return ResponseEntity(appointmentService.findWeeklyAppointments(from, to), HttpStatus.OK)
    }

    fun deleteById(id: String, scheduleId: String): ResponseEntity<String> {
        return ResponseEntity(appointmentService.deleteById(id, scheduleId), HttpStatus.OK)
    }
}

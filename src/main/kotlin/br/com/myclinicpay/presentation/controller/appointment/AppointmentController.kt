package br.com.myclinicpay.presentation.controller.appointment

import br.com.myclinicpay.domain.model.appointment.Appointment
import br.com.myclinicpay.domain.usecases.appointment.AppointmentService
import br.com.myclinicpay.infra.db.mongoDb.entities.AppointmentEntity
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import java.time.LocalDateTime

@Controller
class AppointmentController(private val appointmentService: AppointmentService) {

    fun createAppointment(request: RequestEntity<Appointment>): ResponseEntity<String> {
        val body = request.body ?: throw Exception("Empty body")
        return ResponseEntity(appointmentService.createOrUpdate(body), HttpStatus.CREATED)
    }

    fun findAppointmentByDateAndUser(date: LocalDateTime, userId: String): ResponseEntity<AppointmentEntity> {
        return ResponseEntity(appointmentService.findByDateAndUserId(date, userId), HttpStatus.OK)
    }
}

package br.com.myclinicpay.presentation.factories.appointment

import br.com.myclinicpay.data.service.appointment.AppointmentService
import br.com.myclinicpay.infra.db.mongoDb.repository.appointment.AppointmentRepository
import br.com.myclinicpay.infra.db.mongoDb.repository.person.FindPersonByIdRepository
import br.com.myclinicpay.presentation.controller.appointment.AppointmentController

class AppointmentFactory {
    fun buildCreate(): AppointmentController {
        val appointmentRepository = AppointmentRepository()
        val findPersonById = FindPersonByIdRepository()
        val service = AppointmentService(findPersonById, appointmentRepository)
        return AppointmentController(service)
    }
}
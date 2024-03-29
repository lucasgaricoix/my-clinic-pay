package br.com.myclinicpay.presentation.factories.appointment

import br.com.myclinicpay.data.service.appointment.AppointmentService
import br.com.myclinicpay.infra.db.mongoDb.repository.appointment.AppointmentRepository
import br.com.myclinicpay.infra.db.mongoDb.repository.payment.income.IncomeRepository
import br.com.myclinicpay.infra.db.mongoDb.repository.payment.income.FindAllBySessionIdIncomeRepository
import br.com.myclinicpay.infra.db.mongoDb.repository.payment.type.FindPaymentTypeByIdRepository
import br.com.myclinicpay.infra.db.mongoDb.repository.person.FindPersonByIdRepository
import br.com.myclinicpay.infra.db.mongoDb.repository.user.UserRepository
import br.com.myclinicpay.presentation.controller.appointment.AppointmentController

class AppointmentFactory {
    fun build(): AppointmentController {
        val appointmentRepository = AppointmentRepository()
        val userRepository = UserRepository()
        val findPersonById = FindPersonByIdRepository()
        val createIncomeService = IncomeRepository()
        val findAllBySessionIdIncomeRepository = FindAllBySessionIdIncomeRepository()
        val paymentTypeRepository = FindPaymentTypeByIdRepository()
        val service = AppointmentService(
            findPersonById,
            userRepository,
            appointmentRepository,
            createIncomeService,
            findAllBySessionIdIncomeRepository,
            paymentTypeRepository
        )
        return AppointmentController(service)
    }
}

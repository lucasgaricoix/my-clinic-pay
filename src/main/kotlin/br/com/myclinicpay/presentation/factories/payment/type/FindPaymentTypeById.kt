package br.com.myclinicpay.presentation.factories.payment.type

import br.com.myclinicpay.data.service.payment.type.FindPaymentTypeByIdService
import br.com.myclinicpay.infra.db.mongoDb.repository.payment.type.FindPaymentTypeByIdRepository
import br.com.myclinicpay.presentation.controller.payment.type.FindPaymentTypeByIdController

class FindPaymentTypeById {
    fun build(): FindPaymentTypeByIdController {
        val repository = FindPaymentTypeByIdRepository()
        val service = FindPaymentTypeByIdService(repository)
        return FindPaymentTypeByIdController(service)
    }
}

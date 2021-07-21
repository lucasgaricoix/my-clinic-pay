package br.com.myclinicpay.presentation.factories.payment_type

import br.com.myclinicpay.data.service.payment_type.FindPaymentTypeByIdService
import br.com.myclinicpay.infra.db.mongoDb.repository.payment_type.FindPaymentTypeByIdRepository
import br.com.myclinicpay.presentation.controller.payment_type.FindPaymentTypeByIdController

class FindPaymentTypeById {
    fun build(): FindPaymentTypeByIdController {
        val repository = FindPaymentTypeByIdRepository()
        val service = FindPaymentTypeByIdService(repository)
        return FindPaymentTypeByIdController(service)
    }
}

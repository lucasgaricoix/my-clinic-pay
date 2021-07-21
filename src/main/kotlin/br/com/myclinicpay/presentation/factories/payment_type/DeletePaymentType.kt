package br.com.myclinicpay.presentation.factories.payment_type

import br.com.myclinicpay.data.service.payment_type.DeletePaymentTypeService
import br.com.myclinicpay.infra.mongoDb.repository.payment_type.DeletePaymentTypeByIdRepository
import br.com.myclinicpay.presentation.controller.payment_type.DeletePaymentTypeController

class DeletePaymentType {
    fun build(): DeletePaymentTypeController {
        val repository = DeletePaymentTypeByIdRepository()
        val service = DeletePaymentTypeService(repository)
        return DeletePaymentTypeController(service)
    }
}
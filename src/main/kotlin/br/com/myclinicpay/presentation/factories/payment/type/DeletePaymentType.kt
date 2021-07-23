package br.com.myclinicpay.presentation.factories.payment.type

import br.com.myclinicpay.data.service.payment.type.DeletePaymentPaymentTypeService
import br.com.myclinicpay.infra.db.mongoDb.repository.payment.type.DeletePaymentTypeByIdRepository
import br.com.myclinicpay.presentation.controller.payment.type.DeletePaymentTypeController

class DeletePaymentType {
    fun build(): DeletePaymentTypeController {
        val repository = DeletePaymentTypeByIdRepository()
        val service = DeletePaymentPaymentTypeService(repository)
        return DeletePaymentTypeController(service)
    }
}

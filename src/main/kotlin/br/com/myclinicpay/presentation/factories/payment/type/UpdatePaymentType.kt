package br.com.myclinicpay.presentation.factories.payment.type

import br.com.myclinicpay.data.service.payment.type.UpdatePaymentPaymentTypeService
import br.com.myclinicpay.infra.db.mongoDb.repository.payment.type.UpdatePaymentTypeRepository
import br.com.myclinicpay.presentation.controller.payment.type.UpdatePaymentTypeController

class UpdatePaymentType {
    fun build(): UpdatePaymentTypeController {
        val repository = UpdatePaymentTypeRepository()
        val service = UpdatePaymentPaymentTypeService(repository)
        return UpdatePaymentTypeController(service)
    }
}

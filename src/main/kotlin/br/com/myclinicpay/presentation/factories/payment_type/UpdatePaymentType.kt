package br.com.myclinicpay.presentation.factories.payment_type

import br.com.myclinicpay.data.service.payment_type.UpdatePaymentPaymentTypeService
import br.com.myclinicpay.infra.db.mongoDb.repository.payment_type.UpdatePaymentTypeRepository
import br.com.myclinicpay.presentation.controller.payment_type.UpdatePaymentTypeController

class UpdatePaymentType {
    fun build(): UpdatePaymentTypeController {
        val repository = UpdatePaymentTypeRepository()
        val service = UpdatePaymentPaymentTypeService(repository)
        return UpdatePaymentTypeController(service)
    }
}

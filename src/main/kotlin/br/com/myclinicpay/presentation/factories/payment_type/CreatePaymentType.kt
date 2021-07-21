package br.com.myclinicpay.presentation.factories.payment_type

import br.com.myclinicpay.data.service.payment_type.CreatePaymentPaymentTypeService
import br.com.myclinicpay.infra.db.mongoDb.repository.payment_type.CreatePaymentTypeRepository
import br.com.myclinicpay.presentation.controller.payment_type.CreatePaymentTypeController

class CreatePaymentType {
    fun build() : CreatePaymentTypeController {
        val repository = CreatePaymentTypeRepository()
        val service = CreatePaymentPaymentTypeService(repository)
        return CreatePaymentTypeController(service)
    }
}

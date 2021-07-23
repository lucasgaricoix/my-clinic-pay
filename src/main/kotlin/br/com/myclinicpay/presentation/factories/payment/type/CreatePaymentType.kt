package br.com.myclinicpay.presentation.factories.payment.type

import br.com.myclinicpay.data.service.payment.type.CreatePaymentPaymentTypeService
import br.com.myclinicpay.infra.db.mongoDb.repository.payment.type.CreatePaymentTypeRepository
import br.com.myclinicpay.presentation.controller.payment.type.CreatePaymentTypeController

class CreatePaymentType {
    fun build() : CreatePaymentTypeController {
        val repository = CreatePaymentTypeRepository()
        val service = CreatePaymentPaymentTypeService(repository)
        return CreatePaymentTypeController(service)
    }
}

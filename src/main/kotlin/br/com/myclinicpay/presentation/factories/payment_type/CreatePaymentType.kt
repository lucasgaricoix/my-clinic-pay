package br.com.myclinicpay.presentation.factories.payment_type

import br.com.myclinicpay.data.service.payment_type.CreatePaymentTypeService
import br.com.myclinicpay.infra.mongoDb.repository.payment_type.CreatePaymentTypeRepository
import br.com.myclinicpay.presentation.controller.payment_type.CreatePaymentTypeController

class CreatePaymentType {
    fun build() : CreatePaymentTypeController {
        val repository = CreatePaymentTypeRepository()
        val service = CreatePaymentTypeService(repository)
        return CreatePaymentTypeController(service)
    }
}
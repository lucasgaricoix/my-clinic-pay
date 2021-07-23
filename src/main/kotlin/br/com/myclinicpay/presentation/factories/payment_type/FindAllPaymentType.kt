package br.com.myclinicpay.presentation.factories.payment_type

import br.com.myclinicpay.data.service.payment_type.FindAllPaymentTypeByTypeService
import br.com.myclinicpay.data.service.payment_type.FindAllPaymentTypeService
import br.com.myclinicpay.infra.db.mongoDb.repository.payment_type.FindAllPaymentTypesByTypeRepository
import br.com.myclinicpay.infra.db.mongoDb.repository.payment_type.FindAllPaymentTypesRepository
import br.com.myclinicpay.presentation.controller.payment_type.FindAllPaymentTypeByTypeController
import br.com.myclinicpay.presentation.controller.payment_type.FindAllPaymentTypeController

class FindAllPaymentType {
    fun build(): FindAllPaymentTypeController {
        val repository = FindAllPaymentTypesRepository()
        val service = FindAllPaymentTypeService(repository)
        return FindAllPaymentTypeController(service)
    }

    fun buildByType(): FindAllPaymentTypeByTypeController {
        val repository = FindAllPaymentTypesByTypeRepository()
        val service = FindAllPaymentTypeByTypeService(repository)
        return FindAllPaymentTypeByTypeController(service)
    }
}

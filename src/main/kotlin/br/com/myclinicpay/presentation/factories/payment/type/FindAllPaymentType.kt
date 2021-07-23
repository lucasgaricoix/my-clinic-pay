package br.com.myclinicpay.presentation.factories.payment.type

import br.com.myclinicpay.data.service.payment.type.FindAllPaymentTypeByTypeService
import br.com.myclinicpay.data.service.payment.type.FindAllPaymentTypeService
import br.com.myclinicpay.infra.db.mongoDb.repository.payment.type.FindAllPaymentTypesByTypeRepository
import br.com.myclinicpay.infra.db.mongoDb.repository.payment.type.FindAllPaymentTypesRepository
import br.com.myclinicpay.presentation.controller.payment.type.FindAllPaymentTypeByTypeController
import br.com.myclinicpay.presentation.controller.payment.type.FindAllPaymentTypeController

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

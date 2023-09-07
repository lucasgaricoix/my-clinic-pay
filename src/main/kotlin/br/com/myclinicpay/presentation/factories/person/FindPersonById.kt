package br.com.myclinicpay.presentation.factories.person

import br.com.myclinicpay.data.service.person.FindPersonByIdService
import br.com.myclinicpay.infra.db.mongoDb.repository.payment.type.FindPaymentTypeByIdRepository
import br.com.myclinicpay.infra.db.mongoDb.repository.person.FindPersonByIdRepository
import br.com.myclinicpay.presentation.controller.person.FindPersonByIdController

class FindPersonById {
    fun build(): FindPersonByIdController {
        val repository = FindPersonByIdRepository()
        val findPaymentTypeByIdRepository = FindPaymentTypeByIdRepository()
        val service = FindPersonByIdService(repository, findPaymentTypeByIdRepository)
        return FindPersonByIdController(service)
    }
}

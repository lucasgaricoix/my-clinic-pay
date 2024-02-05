package br.com.myclinicpay.presentation.factories.person

import br.com.myclinicpay.data.service.person.FindPersonService
import br.com.myclinicpay.infra.db.mongoDb.repository.payment.type.FindPaymentTypeByIdRepository
import br.com.myclinicpay.infra.db.mongoDb.repository.person.FindPersonRepository
import br.com.myclinicpay.presentation.controller.person.FindPersonByIdController

class FindPersonById {
    fun build(): FindPersonByIdController {
        val repository = FindPersonRepository()
        val findPaymentTypeByIdRepository = FindPaymentTypeByIdRepository()
        val service = FindPersonService(repository, findPaymentTypeByIdRepository)
        return FindPersonByIdController(service)
    }
}

package br.com.myclinicpay.presentation.factories.person

import br.com.myclinicpay.data.service.person.FindPersonByIdService
import br.com.myclinicpay.infra.db.mongoDb.repository.person.FindPersonByIdRepository
import br.com.myclinicpay.presentation.controller.person.FindPersonByIdController

class FindPersonById {
    fun build(): FindPersonByIdController {
        val repository = FindPersonByIdRepository()
        val service = FindPersonByIdService(repository)
        return FindPersonByIdController(service)
    }
}

package br.com.myclinicpay.presentation.factories.person

import br.com.myclinicpay.data.service.person.CreatePersonService
import br.com.myclinicpay.infra.db.mongoDb.repository.person.CreatePersonRepository
import br.com.myclinicpay.presentation.controller.person.CreatePersonController

class CreatePerson {
    fun build(): CreatePersonController {
        val repository = CreatePersonRepository()
        val service = CreatePersonService(repository)
        return CreatePersonController(service)
    }
}

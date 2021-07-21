package br.com.myclinicpay.presentation.factories.person

import br.com.myclinicpay.data.service.person.FindAllPersonService
import br.com.myclinicpay.infra.db.mongoDb.repository.person.FindAllPersonRepository
import br.com.myclinicpay.presentation.controller.person.FindAllPersonController

class FindAllPerson {
    fun build(): FindAllPersonController {
        val repository = FindAllPersonRepository()
        val service = FindAllPersonService(repository)
        return FindAllPersonController(service)
    }
}

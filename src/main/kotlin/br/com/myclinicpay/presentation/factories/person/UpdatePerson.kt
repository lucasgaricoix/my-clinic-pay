package br.com.myclinicpay.presentation.factories.person

import br.com.myclinicpay.data.service.person.UpdatePersonService
import br.com.myclinicpay.infra.db.mongoDb.repository.person.UpdatePersonRepository
import br.com.myclinicpay.presentation.controller.person.UpdatePersonController

class UpdatePerson {
    fun build(): UpdatePersonController {
        val repository = UpdatePersonRepository()
        val service = UpdatePersonService(repository)
        return UpdatePersonController(service)
    }
}

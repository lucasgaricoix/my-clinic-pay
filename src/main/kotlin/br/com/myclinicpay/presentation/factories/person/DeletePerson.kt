package br.com.myclinicpay.presentation.factories.person

import br.com.myclinicpay.data.service.person.DeletePersonService
import br.com.myclinicpay.infra.db.mongoDb.repository.person.DeletePersonRepository
import br.com.myclinicpay.presentation.controller.person.DeletePersonController

class DeletePerson {
    fun build(): DeletePersonController {
        val repository = DeletePersonRepository()
        val service = DeletePersonService(repository)
        return DeletePersonController(service)
    }
}

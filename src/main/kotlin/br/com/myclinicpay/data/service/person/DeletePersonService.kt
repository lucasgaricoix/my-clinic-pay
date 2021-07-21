package br.com.myclinicpay.data.service.person

import br.com.myclinicpay.data.usecases.person.DeletePersonRepository
import br.com.myclinicpay.domain.usecases.person.DeletePerson

class DeletePersonService(private val repository: DeletePersonRepository) : DeletePerson {
    override fun delete(id: String) {
        return repository.deleteById(id)
    }
}

package br.com.myclinicpay.data.service.person

import br.com.myclinicpay.data.usecases.person.UpdatePersonRepository
import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.domain.usecases.person.UpdatePerson

class UpdatePersonService(private val repository: UpdatePersonRepository) : UpdatePerson {
    override fun update(person: Person): String {
        return repository.update(person)
    }
}

package br.com.myclinicpay.data.service.person

import br.com.myclinicpay.data.usecases.person.CreatePersonRepository
import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.domain.usecases.person.CreatePerson
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class CreatePersonService(val repository: CreatePersonRepository) : CreatePerson {
    override fun create(person: Person?): Person {
        val personBody = person ?: throw Exception("Os dados da pessoa está nulo.")
        return repository.create(personBody)
    }
}

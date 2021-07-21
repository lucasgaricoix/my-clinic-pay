package br.com.myclinicpay.data.service.person

import br.com.myclinicpay.data.usecases.person.FindAllPersonRepository
import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.domain.usecases.person.FindAllPerson
import org.springframework.stereotype.Service

@Service
class FindAllPersonService(val repository: FindAllPersonRepository) : FindAllPerson {
    override fun findAll(): List<Person> {
        return repository.findAll()
    }
}

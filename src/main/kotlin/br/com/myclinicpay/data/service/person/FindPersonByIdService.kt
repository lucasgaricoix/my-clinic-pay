package br.com.myclinicpay.data.service.person

import br.com.myclinicpay.data.usecases.person.FindPersonByIdRepository
import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.domain.usecases.person.FindPersonById
import org.springframework.stereotype.Service

@Service
class FindPersonByIdService(private val repository: FindPersonByIdRepository) : FindPersonById {
    override fun findById(id: String): Person {
        return repository.findById(id)
    }
}

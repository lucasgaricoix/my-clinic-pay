package br.com.myclinicpay.data.service.person

import br.com.myclinicpay.data.usecases.payment.type.FindPaymentTypeByIdRepository
import br.com.myclinicpay.data.usecases.person.CreatePersonRepository
import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.domain.usecases.person.CreatePerson
import org.springframework.stereotype.Service

@Service
class CreatePersonService(
    val repository: CreatePersonRepository,
    private val findPaymentTypeByIdRepository: FindPaymentTypeByIdRepository
) : CreatePerson {
    override fun create(person: Person?): Person {
        val personBody = person ?: throw Exception("Os dados da pessoa está nulo.")
        val personEntity = repository.create(personBody)
        val paymentTypeEntity = personEntity.paymentTypeId?.let { findPaymentTypeByIdRepository.findById(it) }
        return personEntity.toModel(paymentTypeEntity)
    }
}

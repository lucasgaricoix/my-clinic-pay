package br.com.myclinicpay.data.service.person

import br.com.myclinicpay.data.usecases.payment.type.FindPaymentTypeByIdRepository
import br.com.myclinicpay.data.usecases.person.FindAllPersonRepository
import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.domain.usecases.person.FindAllPerson
import org.springframework.stereotype.Service

@Service
class FindAllPersonService(
    val repository: FindAllPersonRepository,
    val findPaymentTypeByIdRepository: FindPaymentTypeByIdRepository
) : FindAllPerson {
    override fun findAll(search: String): List<Person> {
        val person = mutableListOf<Person>()
        val personEntity = repository.findAll(search)
        personEntity.forEach { item ->
            if (item.paymentTypeId != null) {
                val paymentType = findPaymentTypeByIdRepository.findById(item.paymentTypeId)
                person.add(item.toModel(paymentType))
            } else {
                person.add(item.toModel(null))
            }
        }
        return person
    }
}

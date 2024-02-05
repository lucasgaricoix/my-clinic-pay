package br.com.myclinicpay.data.service.person

import br.com.myclinicpay.data.usecases.payment.type.FindPaymentTypeByIdRepository
import br.com.myclinicpay.data.usecases.person.FindPersonRepository
import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.domain.usecases.person.FindPersonById
import org.springframework.stereotype.Service

@Service
class FindPersonService(
    private val repository: FindPersonRepository,
    private val findPaymentTypeByIdRepository: FindPaymentTypeByIdRepository
) : FindPersonById {
    override fun findById(id: String): Person {
        val entity = repository.findById(id)
        val paymentTypeEntity = entity.paymentTypeId?.let { findPaymentTypeByIdRepository.findById(it) }
        return entity.toModel(paymentTypeEntity)
    }
}

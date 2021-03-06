package br.com.myclinicpay.data.service.payment.type

import br.com.myclinicpay.data.usecases.payment.type.FindPaymentTypeByIdRepository
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.usecases.payment.type.FindPaymentTypeById

class FindPaymentTypeByIdService(private val repository: FindPaymentTypeByIdRepository) : FindPaymentTypeById {
    override fun findById(id: String): PaymentType {
        return repository.findById(id)
    }
}

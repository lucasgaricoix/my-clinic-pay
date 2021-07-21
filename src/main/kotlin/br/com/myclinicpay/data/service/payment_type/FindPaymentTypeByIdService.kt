package br.com.myclinicpay.data.service.payment_type

import br.com.myclinicpay.data.usecases.payment_type.FindPaymentTypeByIdRepository
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.usecases.payment.FindPaymentTypeById

class FindPaymentTypeByIdService(private val repository: FindPaymentTypeByIdRepository) : FindPaymentTypeById {
    override fun findById(id: String): PaymentType {
        return repository.findById(id)
    }
}
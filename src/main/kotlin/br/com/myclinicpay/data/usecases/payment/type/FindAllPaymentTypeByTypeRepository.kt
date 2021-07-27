package br.com.myclinicpay.data.usecases.payment.type

import br.com.myclinicpay.domain.model.payment_type.PaymentType

interface FindAllPaymentTypeByTypeRepository {
    fun findAllByType(description: String, type: String): List<PaymentType>
}

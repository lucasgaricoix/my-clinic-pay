package br.com.myclinicpay.data.usecases.payment_type

import br.com.myclinicpay.domain.model.payment_type.PaymentType

interface FindAllPaymentTypeRepository {
    fun findAllPaymentType(): List<PaymentType>
}
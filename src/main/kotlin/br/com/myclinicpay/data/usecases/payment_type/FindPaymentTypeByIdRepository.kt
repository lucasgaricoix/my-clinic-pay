package br.com.myclinicpay.data.usecases.payment_type

import br.com.myclinicpay.domain.model.payment_type.PaymentType

interface FindPaymentTypeByIdRepository {
    fun findById(id: String) : PaymentType
}
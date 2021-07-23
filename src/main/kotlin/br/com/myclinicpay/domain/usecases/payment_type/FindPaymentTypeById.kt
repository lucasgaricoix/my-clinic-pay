package br.com.myclinicpay.domain.usecases.payment_type

import br.com.myclinicpay.domain.model.payment_type.PaymentType

interface FindPaymentTypeById {
    fun findById(id: String) : PaymentType
}

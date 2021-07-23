package br.com.myclinicpay.domain.usecases.payment.type

import br.com.myclinicpay.domain.model.payment_type.PaymentType

interface UpdatePaymentType {
    fun updateById(paymentType: PaymentType): String
}

package br.com.myclinicpay.data.usecases.payment.type

import br.com.myclinicpay.domain.model.payment_type.PaymentType

interface UpdatePaymentTypeRepository {
    fun updateById(paymentType: PaymentType): String
}

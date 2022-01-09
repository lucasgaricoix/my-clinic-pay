package br.com.myclinicpay.domain.model.payment

import br.com.myclinicpay.domain.model.payment_type.PaymentType
import java.time.LocalDateTime

abstract class Payment {
    abstract var id: String?
    abstract var date: LocalDateTime
    abstract var description: String
    abstract var paymentType: PaymentType
}

package br.com.myclinicpay.domain.model.payment

import br.com.myclinicpay.domain.model.payment_type.PaymentType
import java.time.LocalDate

abstract class Payment {
    abstract var id: String?
    abstract var date: LocalDate
    abstract var description: String
    abstract var paymentType: PaymentType
}

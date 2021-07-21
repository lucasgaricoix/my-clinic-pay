package br.com.myclinicpay.domain.model.payment

import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.model.person.Person
import java.time.LocalDateTime

class Payment(
    val id: String?,
    val sessionNumber: Int,
    val date: LocalDateTime,
    val annotation: String,
    val isPaid: Boolean,
    val person: Person,
    val paymentType: PaymentType
)
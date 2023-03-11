package br.com.myclinicpay.domain.model.person

import br.com.myclinicpay.domain.model.payment_type.PaymentType
import java.time.LocalDate

class Person(
    val id: String?,
    val name: String,
    val birthDate: LocalDate,
    val responsible: Responsible,
    val paymentType: PaymentType?
)

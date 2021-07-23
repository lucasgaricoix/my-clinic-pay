package br.com.myclinicpay.domain.model.payment

import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.model.person.Person
import java.time.LocalDate

class Income(
    override var id: String?,
    override var date: LocalDate,
    override var paymentType: PaymentType,
    override var description: String,
    val sessionNumber: Int,
    val isPaid: Boolean = false,
    val isHalfValue: Boolean = false,
    val isAbsence: Boolean = false,
    val person: Person
) : Payment()

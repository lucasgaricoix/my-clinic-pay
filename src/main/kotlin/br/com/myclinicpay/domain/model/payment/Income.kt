package br.com.myclinicpay.domain.model.payment

import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.model.person.Person
import java.time.LocalDate
import java.time.ZoneId

class Income(
    override var id: String?,
    override var date: LocalDate = LocalDate.now(ZoneId.of("America/Sao_Paulo")),
    override var paymentType: PaymentType,
    override var description: String,
    val sessionNumber: Int?,
    val isPaid: Boolean = false,
    val isPartial: Boolean = false,
    val isAbsence: Boolean = false,
    val person: Person
) : Payment()

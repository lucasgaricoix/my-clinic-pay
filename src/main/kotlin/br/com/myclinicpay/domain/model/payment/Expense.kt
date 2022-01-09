package br.com.myclinicpay.domain.model.payment

import br.com.myclinicpay.domain.model.payment_type.PaymentType
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

class Expense(
    override var id: String?,
    override var date: LocalDateTime = LocalDateTime.now(ZoneId.of("America/Sao_Paulo")),
    val dueDate: LocalDate,
    val paymentDate: LocalDate,
    override var paymentType: PaymentType,
    override var description: String,
) : Payment()

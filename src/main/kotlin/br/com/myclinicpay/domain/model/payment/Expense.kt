package br.com.myclinicpay.domain.model.payment

import br.com.myclinicpay.domain.model.payment_type.PaymentType
import java.time.LocalDate

class Expense(
    override var id: String?,
    override var date: LocalDate = LocalDate.now(),
    val dueDate: LocalDate,
    val paymentDate: LocalDate,
    override var paymentType: PaymentType,
    override var description: String,
) : Payment()

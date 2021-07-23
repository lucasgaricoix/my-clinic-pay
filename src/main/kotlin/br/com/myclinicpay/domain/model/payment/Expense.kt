package br.com.myclinicpay.domain.model.payment

import br.com.myclinicpay.domain.model.payment_type.PaymentType
import java.time.LocalDateTime

class Expense(
    override var id: String?,
    override var date: LocalDateTime,
    override var paymentType: PaymentType,
    override var description: String,
) : Payment()

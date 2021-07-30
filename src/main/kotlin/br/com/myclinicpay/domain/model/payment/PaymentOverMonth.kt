package br.com.myclinicpay.domain.model.payment

import java.time.Month

class PaymentOverMonth(
    val month: String,
    val income: Double,
    val expense: Double,
    val amount: Double
)

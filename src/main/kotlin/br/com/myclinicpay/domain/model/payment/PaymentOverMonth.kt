package br.com.myclinicpay.domain.model.payment

class PaymentOverMonth(
    val year: Int,
    val month: String,
    val income: Double,
    val expense: Double,
    val amount: Double
)

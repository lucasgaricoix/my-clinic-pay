package br.com.myclinicpay.domain.model.payment_type

class PaymentType(
    val id: String?,
    val type: TypeEnum,
    val description: String,
    val value: Double
)

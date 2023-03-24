package br.com.myclinicpay.domain.model.payment_type

data class PaymentType(
    val id: String?,
    val type: TypeEnum,
    val description: String,
    val value: Double,
    val color: String?
) {
    constructor(
        id: String?,
        type: TypeEnum,
        description: String,
        value: Double
    ) : this(id, type, description, value, null)
}

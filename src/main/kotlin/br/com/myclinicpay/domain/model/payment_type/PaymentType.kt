package br.com.myclinicpay.domain.model.payment_type

import br.com.myclinicpay.infra.db.mongoDb.entities.PaymentTypeEntity
import org.bson.types.ObjectId

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

    fun toEntity() = PaymentTypeEntity(
        id = ObjectId(id),
        type = type.value.uppercase(),
        description = description,
        value = value,
        color = color
    )
}

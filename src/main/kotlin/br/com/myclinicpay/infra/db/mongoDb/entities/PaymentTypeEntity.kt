package br.com.myclinicpay.infra.db.mongoDb.entities

import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.model.payment_type.TypeEnum
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("payment_type")
data class PaymentTypeEntity(
    @Id
    val id: ObjectId?,
    val type: String,
    val description: String,
    val value: Double,
    val color: String?
) {
    constructor(
        id: ObjectId?,
        type: String,
        description: String,
        value: Double,
    ) : this(id, type, description, value, null)

    fun toModel(): PaymentType {
        return PaymentType(
            this.id.toString(),
            TypeEnum.valueOf(this.type.uppercase()),
            this.description,
            this.value
        )
    }
}

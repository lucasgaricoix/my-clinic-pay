package br.com.myclinicpay.infra.db.mongoDb.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

@Document("payment")
class PaymentEntity(
    @Id
    val id: ObjectId,
    val sessionNumber: Int,
    val date: LocalDateTime,
    val annotation: String,
    val isPaid: Boolean,
    @Field("person")
    val person: PersonEntity,
    @Field("typevalue")
    val paymentType: PaymentTypeEntity
)

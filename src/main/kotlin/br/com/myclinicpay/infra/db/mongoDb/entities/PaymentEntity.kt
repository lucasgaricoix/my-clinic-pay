package br.com.myclinicpay.infra.db.mongoDb.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.ZoneId

@Document("payment")
class PaymentEntity(
    @Id
    val id: ObjectId,
    val date: LocalDate = LocalDate.now(ZoneId.of("America/Sao_Paulo")),
    val paymentType: PaymentTypeEntity,
    val description: String,
)

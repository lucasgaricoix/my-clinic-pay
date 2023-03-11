package br.com.myclinicpay.infra.db.mongoDb.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document("person")
data class PersonEntity(
    @Id
    val id: ObjectId? = ObjectId.get(),
    val name: String,
    val birthDate: LocalDate,
    val responsible: ResponsibleEntity,
    val paymentType: PaymentTypeEntity?
)

package br.com.myclinicpay.infra.db.mongoDb.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document("income")
class IncomeEntity(
    @Id
    val id: ObjectId,
    val date: LocalDate,
    val paymentType: PaymentTypeEntity,
    val description: String,
    val sessionNumber: Int,
    val isPaid: Boolean,
    val isPartial: Boolean = false,
    val isAbsence: Boolean = false,
    val person: PersonEntity
)

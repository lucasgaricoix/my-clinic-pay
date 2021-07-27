package br.com.myclinicpay.infra.db.mongoDb.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document("expense")
class ExpenseEntity(
    @Id
    val id: ObjectId,
    val date: LocalDate,
    val dueDate: LocalDate = LocalDate.now(),
    val paymnetDate: LocalDate = LocalDate.now(),
    val paymentType: PaymentTypeEntity,
    val description: String,
)

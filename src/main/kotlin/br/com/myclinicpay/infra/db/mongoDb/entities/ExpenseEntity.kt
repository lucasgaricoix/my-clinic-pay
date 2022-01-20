package br.com.myclinicpay.infra.db.mongoDb.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.ZoneId

@Document("expense")
class ExpenseEntity(
    @Id
    val id: ObjectId,
    val date: LocalDate = LocalDate.now(ZoneId.of("America/Sao_Paulo")),
    val dueDate: LocalDate = LocalDate.now(),
    val paymentDate: LocalDate = LocalDate.now(),
    val paymentType: PaymentTypeEntity,
    val description: String,
)

package br.com.myclinicpay.infra.db.mongoDb.entities

import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.model.payment_type.TypeEnum
import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.domain.model.person.Responsible
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
) {
    fun toDomainModel(): Income {
        return Income(
            this.id.toString(),
            this.date,
            PaymentType(
                this.paymentType.id.toString(),
                TypeEnum.valueOf(this.paymentType.type.uppercase()),
                this.paymentType.description,
                this.paymentType.value
            ),
            this.description,
            this.sessionNumber,
            this.isPaid,
            this.isPartial,
            this.isAbsence,
            Person(
                this.person.id.toString(),
                this.person.name,
                this.person.birthDate,
                Responsible(
                    this.person.responsible.id.toString(),
                    this.person.responsible.name
                ),
                PaymentType(
                    this.paymentType.id.toString(),
                    TypeEnum.valueOf(this.paymentType.type.uppercase()),
                    this.paymentType.description,
                    this.paymentType.value
                ),
            )
        )
    }
}

package br.com.myclinicpay.infra.db.mongoDb.entities

import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.model.payment_type.TypeEnum
import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.domain.model.person.Responsible
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
) {
    fun toModel(): Person {
        return Person(
            this.id.toString(),
            this.name,
            this.birthDate,
            Responsible(
                this.responsible.id.toString(),
                this.responsible.name
            ),
            this.paymentType?.let {
                PaymentType(
                    it.id.toString(),
                    TypeEnum.valueOf(it.type.uppercase()),
                    it.description,
                    it.value
                )
            }
        )
    }
}

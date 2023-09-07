package br.com.myclinicpay.domain.model.person

import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.infra.db.mongoDb.entities.PaymentTypeEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.PersonEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.ResponsibleEntity
import org.bson.types.ObjectId
import java.time.LocalDate

data class Person(
    val id: String?,
    val name: String,
    val birthDate: LocalDate,
    val responsible: Responsible,
    val paymentType: PaymentType?
) {
    fun toEntity(): PersonEntity {
        return PersonEntity(
            ObjectId.get(),
            this.name,
            this.birthDate,
            ResponsibleEntity(
                ObjectId.get(),
                this.responsible.name
            ),
            this.paymentType?.id
        )
    }
}

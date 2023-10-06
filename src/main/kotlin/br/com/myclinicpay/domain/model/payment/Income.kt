package br.com.myclinicpay.domain.model.payment

import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.infra.db.mongoDb.entities.IncomeEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.PaymentTypeEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.PersonEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.ResponsibleEntity
import org.bson.types.ObjectId
import java.time.LocalDate
import java.time.ZoneId

class Income(
    override var id: String?,
    override var date: LocalDate = LocalDate.now(ZoneId.of("America/Sao_Paulo")),
    override var paymentType: PaymentType,
    override var description: String,
    val sessionNumber: Int?,
    val isPaid: Boolean = false,
    val isPartial: Boolean = false,
    val isAbsence: Boolean = false,
    val person: Person,
    val scheduleId: String? = null
) : Payment() {
    fun toEntity(lastSession: Int?): IncomeEntity {
        var scheduleIdEntity = ObjectId()
        if (scheduleId != null) {
            scheduleIdEntity = ObjectId(scheduleId)
        }
        var value = this.paymentType.value
        if (this.isPartial) {
            value = this.paymentType.value / 2
        }

        if (this.isAbsence) {
            value = 0.0
        }

        return IncomeEntity(
            id = ObjectId.get(),
            date = this.date,
            paymentType = PaymentTypeEntity(
                ObjectId(this.paymentType.id),
                this.paymentType.type.value,
                this.paymentType.description,
                value
            ),
            description = this.description,
            sessionNumber = this.sessionNumber ?: lastSession ?: 0,
            isPaid = this.isPaid,
            isPartial = this.isPartial,
            isAbsence = this.isAbsence,
            person = PersonEntity(
                ObjectId(this.person.id),
                this.person.name,
                this.person.birthDate,
                ResponsibleEntity(
                    ObjectId(this.person.responsible.id),
                    this.person.responsible.name
                ),
                this.paymentType.id,
            ),
            scheduleId = scheduleIdEntity
        )
    }
}

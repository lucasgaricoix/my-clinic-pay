package br.com.myclinicpay.infra.db.mongoDb.repository.payment.income

import br.com.myclinicpay.data.usecases.payment.income.CreateIncomeRepository
import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.model.payment_type.TypeEnum
import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.domain.model.person.Responsible
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.IncomeEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.PaymentTypeEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.PersonEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.ResponsibleEntity
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.client.HttpServerErrorException

@Repository
class CreateIncomeRepository : CreateIncomeRepository {
    private val collectionName = "income"
    override fun create(income: Income, paymentType: PaymentType, person: Person): Income {
        try {
            val mongoTemplate = Connection.getTemplate()
            val created = mongoTemplate.save<IncomeEntity>(toEntity(income, paymentType, person), collectionName)
            return toDomainModel(created)
        } catch (error: Exception) {
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível persistir o pagament\n$error")
        }
    }

    private fun toDomainModel(incomeEntity: IncomeEntity): Income {
        return Income(
            incomeEntity.id.toString(),
            incomeEntity.date,
            PaymentType(
                incomeEntity.paymentType.id.toString(),
                TypeEnum.valueOf(incomeEntity.paymentType.type.uppercase()),
                incomeEntity.paymentType.description,
                incomeEntity.paymentType.value
            ),
            incomeEntity.description,
            incomeEntity.sessionNumber,
            incomeEntity.isPaid,
            incomeEntity.isHalfValue,
            incomeEntity.isAbsence,
            Person(
                incomeEntity.person.id.toString(),
                incomeEntity.person.name,
                incomeEntity.person.birthDate,
                Responsible(
                    incomeEntity.person.responsible.id.toString(),
                    incomeEntity.person.responsible.name
                )
            )
        )
    }

    private fun toEntity(
        income: Income,
        paymentType: PaymentType,
        person: Person
    ): IncomeEntity {
        return IncomeEntity(
            ObjectId.get(),
            income.date,
            PaymentTypeEntity(
                ObjectId(paymentType.id),
                paymentType.type.value,
                paymentType.description,
                paymentType.value
            ),
            income.description,
            income.sessionNumber,
            income.isPaid,
            income.isHalfValue,
            income.isAbsence,
            PersonEntity(
                ObjectId(person.id),
                person.name,
                person.birthDate,
                ResponsibleEntity(
                    ObjectId(person.responsible.id),
                    person.responsible.name
                )
            ),
        )
    }
}

package br.com.myclinicpay.infra.db.mongoDb.repository.payment.income

import br.com.myclinicpay.data.usecases.payment.income.CreateIncomeRepository
import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.*
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.client.HttpServerErrorException

@Repository
class CreateIncomeRepository : CreateIncomeRepository {
    override fun create(income: Income, lastSession: Int?): Income {
        try {
            val mongodbTemplate = Connection.getTemplate()
            val created = mongodbTemplate.save<IncomeEntity>(toEntity(income, lastSession), "income")
            mongodbTemplate.save(toPaymentEntity(created))
            return created.toDomainModel()
        } catch (error: Exception) {
            throw HttpServerErrorException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Não foi possível persistir o pagament\n$error"
            )
        }
    }

    private fun toEntity(income: Income, lastSession: Int?): IncomeEntity {
        var value = income.paymentType.value
        if (income.isPartial) {
            value = income.paymentType.value / 2
        }

        if (income.isAbsence) {
            value = 0.0
        }

        return IncomeEntity(
            ObjectId.get(),
            income.date,
            PaymentTypeEntity(
                ObjectId(income.paymentType.id),
                income.paymentType.type.value,
                income.paymentType.description,
                value
            ),
            income.description,
            income.sessionNumber ?: lastSession ?: 0,
            income.isPaid,
            income.isPartial,
            income.isAbsence,
            PersonEntity(
                ObjectId(income.person.id),
                income.person.name,
                income.person.birthDate,
                ResponsibleEntity(
                    ObjectId(income.person.responsible.id),
                    income.person.responsible.name
                ),
                income.person.paymentType?.let {
                    PaymentTypeEntity(
                        ObjectId(it.id),
                        it.type.value,
                        it.description,
                        it.value
                    )
                }
            ),
        )
    }

    private fun toPaymentEntity(income: IncomeEntity): PaymentEntity {
        return PaymentEntity(
            income.id,
            income.date,
            PaymentTypeEntity(
                income.paymentType.id,
                income.paymentType.type,
                income.paymentType.description,
                income.paymentType.value
            ),
            income.description
        )
    }
}

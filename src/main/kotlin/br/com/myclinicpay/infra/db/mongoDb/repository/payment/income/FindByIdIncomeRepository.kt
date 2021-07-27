package br.com.myclinicpay.infra.db.mongoDb.repository.payment.income

import br.com.myclinicpay.data.usecases.payment.income.FindByIdIncomeRepository
import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.model.payment_type.TypeEnum
import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.domain.model.person.Responsible
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.IncomeEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.findById
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.client.HttpServerErrorException

@Repository
class FindByIdIncomeRepository : FindByIdIncomeRepository {
    override fun findById(id: String): Income {
        val mongoTemplate = Connection.getTemplate()
        val incomeEntity = mongoTemplate.findById<IncomeEntity>(ObjectId(id), "income")
            ?: throw HttpServerErrorException(
                HttpStatus.NOT_FOUND,
                "Não foi possível encontrar o pagamento com o id $id"
            )

        return toDomainModel(incomeEntity)
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
            incomeEntity.isPartial,
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
}

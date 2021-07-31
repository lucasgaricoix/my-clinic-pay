package br.com.myclinicpay.infra.db.mongoDb.repository.payment.income

import br.com.myclinicpay.data.usecases.payment.income.FindAllIncomeRepository
import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.model.payment_type.TypeEnum
import br.com.myclinicpay.domain.model.person.Person
import br.com.myclinicpay.domain.model.person.Responsible
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.IncomeEntity
import org.springframework.data.mongodb.core.aggregate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.find
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Repository

@Repository
class FindAllIncomeRepository : FindAllIncomeRepository {
    override fun findAll(search: Int): List<Income> {
        val mongoTemplate = Connection.getTemplate()
        val projection = Aggregation.project("id")
            .and("date").`as`("date")
            .and("paymentType").`as`("paymentType")
            .and("description").`as`("description")
            .and("sessionNumber").`as`("sessionNumber")
            .and("isPaid").`as`("isPaid")
            .and("isPartial").`as`("isPartial")
            .and("isAbsence").`as`("isAbsence")
            .and("person").`as`("person")
            .andExpression("month(date)").`as`("month")

        val match = Aggregation.match(Criteria.where("month").`is`(search))
        val data = mongoTemplate.aggregate<IncomeEntity>(Aggregation.newAggregation(projection, match), "income")
        return data.mappedResults.toList().map { toDomainModel(it) }
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

package br.com.myclinicpay.infra.db.mongoDb.repository.payment.income

import br.com.myclinicpay.data.usecases.payment.income.FindAllIncomeRepository
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.IncomeEntity
import org.springframework.data.mongodb.core.aggregate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Repository

@Repository
class FindAllIncomeRepository : FindAllIncomeRepository {
    override fun findAll(month: Int, year: Int): List<IncomeEntity> {
        val mongodbTemplate = Connection.getTemplate()
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
            .andExpression("year(date)").`as`("year")

        val matchMonth = Aggregation.match(Criteria.where("month").`is`(month))
        val matchYear = Aggregation.match(Criteria.where("year").`is`(year))
        val data = mongodbTemplate.aggregate<IncomeEntity>(
            Aggregation.newAggregation(projection, matchMonth, matchYear),
            "income"
        )

        return data.mappedResults.toList()
    }
}

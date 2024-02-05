package br.com.myclinicpay.infra.db.mongoDb.repository.payment.income

import br.com.myclinicpay.data.usecases.payment.income.FindIncomeRepository
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.IncomeEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.aggregate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.find
import org.springframework.data.mongodb.core.findById
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.client.HttpServerErrorException
import java.time.LocalDate

@Repository
class FindIncomeRepository : FindIncomeRepository {
    override fun findById(id: String): IncomeEntity {
        val mongoTemplate = Connection.getTemplate()

        return mongoTemplate.findById<IncomeEntity>(ObjectId(id), "income")
            ?: throw HttpServerErrorException(
                HttpStatus.NOT_FOUND,
                "Não foi possível encontrar o pagamento com o id $id"
            )
    }

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

    override fun findLastSession(initialRange: LocalDate, finalRange: LocalDate): Int {
        val mongodbTemplate = Connection.getTemplate()
        val query = Query(Criteria.where("date").gte(initialRange).lte(finalRange))
        val sortedIncomes =
            mongodbTemplate.find<IncomeEntity>(query, "income").sortedByDescending { it.sessionNumber }.toList()
        val lastSession = sortedIncomes.firstOrNull()?.sessionNumber ?: 1
        return lastSession.inc()
    }
}

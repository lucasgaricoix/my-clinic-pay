package br.com.myclinicpay.infra.db.mongoDb.repository.payment.income

import br.com.myclinicpay.data.usecases.payment.income.FindAllBySessionIdIncomeRepository
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.IncomeEntity
import org.springframework.data.mongodb.core.find
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class FindAllBySessionIdIncomeRepository : FindAllBySessionIdIncomeRepository {
    override fun findAll(initialRange: LocalDate, finalRange: LocalDate): Int {
        val mongodbTemplate = Connection.getTemplate()
        val query = Query(Criteria.where("date").gte(initialRange).lte(finalRange))
        val sortedIncomes =
            mongodbTemplate.find<IncomeEntity>(query, "income").sortedByDescending { it.sessionNumber }.toList()
        val lastSession = sortedIncomes.firstOrNull()?.sessionNumber ?: 1
        return lastSession.inc()
    }
}

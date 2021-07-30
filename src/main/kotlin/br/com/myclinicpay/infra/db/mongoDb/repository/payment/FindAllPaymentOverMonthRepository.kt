package br.com.myclinicpay.infra.db.mongoDb.repository.payment

import br.com.myclinicpay.data.usecases.payment.FindAllPaymentOverMonthRepository
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.PaymentEntity
import org.springframework.data.mongodb.core.find
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class FindAllPaymentOverMonthRepository : FindAllPaymentOverMonthRepository {
    override fun findAllOverMonth(
        initialDate: LocalDate,
        finalDate: LocalDate
    ): List<PaymentEntity> {
        val mongoTemplate = Connection.getTemplate()
        val query = Query(Criteria("date").gte(initialDate).lte(finalDate))
        return mongoTemplate.find<PaymentEntity>(query, "payment").toList()
    }
}

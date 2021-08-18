package br.com.myclinicpay.infra.db.mongoDb.repository.payment.income

import br.com.myclinicpay.data.usecases.payment.income.PayIncomeRepository
import br.com.myclinicpay.infra.db.mongoDb.Connection
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.client.HttpServerErrorException

@Repository
class PayIncomeRepository : PayIncomeRepository {
    override fun pay(id: String): String {
        try {
            val mongoTemplate = Connection.getTemplate()
            val query = Query(Criteria.where("_id").isEqualTo(ObjectId(id)))
            val toUpdate = Update()
                .set("isPaid", true)

            val updated = mongoTemplate.updateFirst(query, toUpdate, "income")
            if (updated.modifiedCount <= 0) {
                throw HttpServerErrorException(HttpStatus.BAD_REQUEST, "Nenhum dado foi atualizado.")
            }
            return updated.upsertedId.toString()
        } catch (error: Exception) {
            throw HttpServerErrorException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Não foi possível persistir o pagamento.\n$error"
            )
        }
    }
}

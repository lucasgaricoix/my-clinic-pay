package br.com.myclinicpay.infra.db.mongoDb.repository.payment.income

import br.com.myclinicpay.data.usecases.payment.income.UpdateIncomeRepository
import br.com.myclinicpay.domain.model.payment.Income
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
class UpdateIncomeRepository : UpdateIncomeRepository {
    private val collectionName = "income"
    override fun update(income: Income): String {
        try {
            val mongoTemplate = Connection.getTemplate()
            val query = Query(Criteria.where("_id").isEqualTo(ObjectId(income.id)))
            val toUpdate = Update()
                .set("date", income.date)
                .set("description", income.description)
                .set("sessionNumber", income.sessionNumber)
                .set("paymentType", income.paymentType)
                .set("isPaid", income.isPaid)
                .set("isPartial", income.isPartial)
                .set("isAbsence", income.isAbsence)
                .set("person", income.person)

            val updated = mongoTemplate.updateFirst(query, toUpdate, collectionName)
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

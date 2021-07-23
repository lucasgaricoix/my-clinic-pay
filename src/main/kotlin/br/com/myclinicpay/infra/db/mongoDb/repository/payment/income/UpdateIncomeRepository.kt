package br.com.myclinicpay.infra.db.mongoDb.repository.payment.income

import br.com.myclinicpay.data.usecases.payment.income.UpdateIncomeRepository
import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.infra.db.mongoDb.Connection
import org.bson.Document
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.client.HttpServerErrorException

@Repository
class UpdateIncomeRepository : UpdateIncomeRepository {
    private val collectionName = "income"
    override fun update(income: Income): String {
        try {
            val mongoCollection = Connection.getTemplate().getCollection(collectionName)
            val query = Document("_id", income.id)
            val toUpdate = Document("date", income.date)
                .append("paymentType", income.paymentType)
                .append("description", income.description)
                .append("sessionNumber", income.sessionNumber)
                .append("isPaid", income.isPaid)
                .append("isHalfValue", income.isHalfValue)
                .append("isAbsence", income.isAbsence)
                .append("person", income.person)

            val updated = mongoCollection.updateOne(query, Document("\$set", toUpdate))
            if (!updated.wasAcknowledged()) {
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

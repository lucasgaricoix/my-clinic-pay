package br.com.myclinicpay.infra.db.mongoDb.repository.payment.expense

import br.com.myclinicpay.data.usecases.payment.expense.DeleteExpenseRepository
import br.com.myclinicpay.infra.db.mongoDb.Connection
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.client.HttpServerErrorException

@Repository
class DeleteExpenseRepository : DeleteExpenseRepository {
    override fun deleteById(id: String) {
        try {
            val mongoTemplate = Connection.getTemplate()
            val mongoCollection = mongoTemplate.getCollection("expense")
            val deleted = mongoCollection.deleteOne(Document("_id", ObjectId(id)))
            if (!deleted.wasAcknowledged()) {
                throw HttpServerErrorException(
                    HttpStatus.NOT_FOUND,
                    "Não foi possível encontrar a despesa para remover."
                )
            }
            mongoTemplate.getCollection("payment").deleteOne(Document("_id", ObjectId(id)))
        } catch (error: Exception) {
            throw HttpServerErrorException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Não foi possível remover a despesa.\n$error"
            )
        }
    }
}

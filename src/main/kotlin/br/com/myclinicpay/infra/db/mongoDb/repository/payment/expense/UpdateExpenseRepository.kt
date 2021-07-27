package br.com.myclinicpay.infra.db.mongoDb.repository.payment.expense

import br.com.myclinicpay.data.usecases.payment.expense.UpdateExpenseRepository
import br.com.myclinicpay.domain.model.payment.Expense
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.ExpenseEntity
import org.bson.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.data.mongodb.core.update
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.client.HttpServerErrorException

@Repository
class UpdateExpenseRepository : UpdateExpenseRepository {
    override fun update(expense: Expense): String {
        try {
            val mongoTemplate = Connection.getTemplate()
            val query = Query(Criteria.where("_id").isEqualTo(expense.id))
            val toUpdate = Update().set("date", expense.date)
                .set("dueDate", expense.dueDate)
                .set("paymentType", expense.paymentType)
                .set("description", expense.description)

            val updated = mongoTemplate.updateFirst(query, toUpdate, "expense")
            if (!updated.wasAcknowledged()) {
                throw HttpServerErrorException(HttpStatus.BAD_REQUEST, "Nenhum dado foi atualizado.")
            }
            return updated.upsertedId.toString()
        } catch (error: Exception) {
            throw HttpServerErrorException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Não foi possível persistir a despesa.\n$error"
            )
        }
    }
}

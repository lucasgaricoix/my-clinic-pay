package br.com.myclinicpay.infra.db.mongoDb.repository.payment.expense

import br.com.myclinicpay.data.usecases.payment.expense.FindByIdExpenseRepository
import br.com.myclinicpay.domain.model.payment.Expense
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.model.payment_type.TypeEnum
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.ExpenseEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.findById
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.client.HttpServerErrorException

@Repository
class FindByIdExpenseRepository :
    FindByIdExpenseRepository {
    override fun findById(id: String): Expense {
        val mongoTemplate = Connection.getTemplate()
        val incomeEntity = mongoTemplate.findById<ExpenseEntity>(ObjectId(id), "expense")
            ?: throw HttpServerErrorException(
                HttpStatus.NOT_FOUND,
                "Não foi possível encontrar o pagamento com o id $id"
            )

        return toDomainModel(incomeEntity)
    }

    private fun toDomainModel(expenseEntity: ExpenseEntity): Expense {
        return Expense(
            expenseEntity.id.toString(),
            expenseEntity.date,
            expenseEntity.dueDate,
            expenseEntity.paymentDate,
            PaymentType(
                expenseEntity.paymentType.id.toString(),
                TypeEnum.valueOf(expenseEntity.paymentType.type),
                expenseEntity.paymentType.description,
                expenseEntity.paymentType.value
            ),
            expenseEntity.description

        )
    }
}

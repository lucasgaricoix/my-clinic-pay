package br.com.myclinicpay.infra.db.mongoDb.repository.payment.expense

import br.com.myclinicpay.data.usecases.payment.expense.FindAllExpenseRepository
import br.com.myclinicpay.domain.model.payment.Expense
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.model.payment_type.TypeEnum
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.ExpenseEntity
import org.springframework.data.mongodb.core.findAll
import org.springframework.stereotype.Repository

@Repository
class FindAllExpenseRepository : FindAllExpenseRepository {
    override fun findAll(): List<Expense> {
        val mongoTemplate = Connection.getTemplate()
        return mongoTemplate.findAll<ExpenseEntity>("expense").map { toDomainModel(it) }.toList()
    }

    private fun toDomainModel(expenseEntity: ExpenseEntity): Expense {
        return Expense(
            expenseEntity.id.toString(),
            expenseEntity.date,
            expenseEntity.dueDate,
            expenseEntity.paymentDate,
            PaymentType(
                expenseEntity.paymentType.id.toString(),
                TypeEnum.valueOf(expenseEntity.paymentType.type.uppercase()),
                expenseEntity.paymentType.description,
                expenseEntity.paymentType.value
            ),
            expenseEntity.description

        )
    }
}

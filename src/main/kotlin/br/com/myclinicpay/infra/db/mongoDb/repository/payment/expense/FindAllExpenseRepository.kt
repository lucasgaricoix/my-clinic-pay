package br.com.myclinicpay.infra.db.mongoDb.repository.payment.expense

import br.com.myclinicpay.data.usecases.payment.expense.FindAllExpenseRepository
import br.com.myclinicpay.domain.model.payment.Expense
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.model.payment_type.TypeEnum
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.ExpenseEntity
import org.springframework.data.mongodb.core.aggregate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Repository

@Repository
class FindAllExpenseRepository : FindAllExpenseRepository {
    override fun findAll(month: Int): List<Expense> {
        val mongoTemplate = Connection.getTemplate()

        val projection = Aggregation.project("id")
            .and("date").`as`("date")
            .and("dueDate").`as`("dueDate")
            .and("paymentDate").`as`("paymentDate")
            .and("paymentType").`as`("paymentType")
            .and("description").`as`("description")
            .andExpression("month(date)").`as`("month")

        val match = Aggregation.match(Criteria.where("month").`is`(month))
        val data = mongoTemplate.aggregate<ExpenseEntity>(Aggregation.newAggregation(projection, match), "expense")
        return data.mappedResults.toList().map { toDomainModel(it) }
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

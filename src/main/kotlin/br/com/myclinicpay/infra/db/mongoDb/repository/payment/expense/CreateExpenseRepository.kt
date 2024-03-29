package br.com.myclinicpay.infra.db.mongoDb.repository.payment.expense

import br.com.myclinicpay.data.usecases.payment.expense.CreateExpenseRepository
import br.com.myclinicpay.domain.model.payment.Expense
import br.com.myclinicpay.domain.model.payment_type.PaymentType
import br.com.myclinicpay.domain.model.payment_type.TypeEnum
import br.com.myclinicpay.infra.db.mongoDb.Connection
import br.com.myclinicpay.infra.db.mongoDb.entities.ExpenseEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.PaymentEntity
import br.com.myclinicpay.infra.db.mongoDb.entities.PaymentTypeEntity
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.client.HttpServerErrorException

@Repository
class CreateExpenseRepository : CreateExpenseRepository {
    override fun create(expense: Expense): Expense {
        try {
            val mongoTemplate = Connection.getTemplate()
            val created = mongoTemplate.save<ExpenseEntity>(toEntity(expense), "expense")
            mongoTemplate.save(toPaymentEntity(created))
            return toDomainModel(created)
        } catch (error: Exception) {
            throw HttpServerErrorException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Não foi possível persistir o pagament\n$error"
            )
        }
    }

    private fun toPaymentEntity(
        expense: ExpenseEntity,
    ): PaymentEntity {
        return PaymentEntity(
            ObjectId.get(),
            expense.date,
            PaymentTypeEntity(
                expense.paymentType.id,
                expense.paymentType.type,
                expense.paymentType.description,
                expense.paymentType.value
            ),
            expense.description
        )
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

    private fun toEntity(expense: Expense): ExpenseEntity {
        return ExpenseEntity(
            ObjectId.get(),
            expense.date,
            expense.dueDate,
            expense.paymentDate,
            PaymentTypeEntity(
                ObjectId(expense.paymentType.id),
                expense.paymentType.type.value,
                expense.paymentType.description,
                expense.paymentType.value
            ),
            expense.description
        )
    }
}

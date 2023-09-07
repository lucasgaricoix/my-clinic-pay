package br.com.myclinicpay.data.service.payment.expense

import br.com.myclinicpay.data.usecases.payment.expense.CreateExpenseRepository
import br.com.myclinicpay.domain.model.payment.Expense
import br.com.myclinicpay.domain.usecases.payment.expense.CreateExpense
import org.springframework.stereotype.Service

@Service
class CreateExpenseService(private val createExpenseRepository: CreateExpenseRepository) : CreateExpense {
    override fun create(expense: Expense): Expense {
        return createExpenseRepository.create(expense)
    }
}

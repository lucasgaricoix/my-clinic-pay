package br.com.myclinicpay.data.service.payment.expense

import br.com.myclinicpay.data.usecases.payment.expense.UpdateExpenseRepository
import br.com.myclinicpay.domain.model.payment.Expense
import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.usecases.payment.expense.UpdateExpense
import br.com.myclinicpay.domain.usecases.payment.income.UpdateIncome
import org.springframework.stereotype.Service

@Service
class UpdateExpenseService(private val repository: UpdateExpenseRepository): UpdateExpense {
    override fun update(expense: Expense): String {
        return repository.update(expense)
    }
}

package br.com.myclinicpay.data.service.payment.expense

import br.com.myclinicpay.data.usecases.payment.expense.FindByIdExpenseRepository
import br.com.myclinicpay.domain.model.payment.Expense
import br.com.myclinicpay.domain.model.payment.Income
import br.com.myclinicpay.domain.usecases.payment.expense.FindByIdExpense
import br.com.myclinicpay.domain.usecases.payment.income.FindByIdIncome
import org.springframework.stereotype.Service

@Service
class FindByIdExpenseService(private val repository: FindByIdExpenseRepository) : FindByIdExpense {
    override fun findById(id: String): Expense {
        return repository.findById(id)
    }
}

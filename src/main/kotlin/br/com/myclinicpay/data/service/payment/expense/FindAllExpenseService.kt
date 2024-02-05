package br.com.myclinicpay.data.service.payment.expense

import br.com.myclinicpay.data.usecases.payment.expense.FindAllExpenseRepository
import br.com.myclinicpay.domain.model.payment.Expense
import br.com.myclinicpay.domain.usecases.payment.expense.FindAllExpense
import org.springframework.stereotype.Service

@Service
class FindAllExpenseService(private val repository: FindAllExpenseRepository) : FindAllExpense  {
    override fun findAll(month: Int): List<Expense> {
        return repository.findAll(month)
    }
}

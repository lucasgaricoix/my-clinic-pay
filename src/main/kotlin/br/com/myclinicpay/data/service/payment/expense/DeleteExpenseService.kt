package br.com.myclinicpay.data.service.payment.expense

import br.com.myclinicpay.data.usecases.payment.expense.DeleteExpenseRepository
import br.com.myclinicpay.domain.usecases.payment.expense.DeleteExpense
import br.com.myclinicpay.domain.usecases.payment.income.DeleteIncome
import org.springframework.stereotype.Service

@Service
class DeleteExpenseService(private val repository: DeleteExpenseRepository) : DeleteExpense {
    override fun delete(id: String) {
        return repository.deleteById(id)
    }
}

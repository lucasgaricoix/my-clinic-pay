package br.com.myclinicpay.data.service.payment.expense

import br.com.myclinicpay.data.usecases.payment.expense.CreateExpenseRepository
import br.com.myclinicpay.data.usecases.payment.type.FindPaymentTypeByIdRepository
import br.com.myclinicpay.domain.model.payment.Expense
import br.com.myclinicpay.domain.usecases.payment.expense.CreateExpense
import org.springframework.stereotype.Service

@Service
class CreateExpenseService(
    private val createExpenseRepository: CreateExpenseRepository,
    private val findPaymentTypeByIdRepository: FindPaymentTypeByIdRepository,
) : CreateExpense {
    override fun create(expense: Expense): Expense {
        val paymentType = findPaymentTypeByIdRepository.findById(expense.paymentType.id.orEmpty())
        return createExpenseRepository.create(expense, paymentType)
    }
}

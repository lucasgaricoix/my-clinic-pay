package br.com.myclinicpay.data.usecases.payment.expense

import br.com.myclinicpay.domain.model.payment.Expense

interface UpdateExpenseRepository {
    fun update(expense: Expense): String
}

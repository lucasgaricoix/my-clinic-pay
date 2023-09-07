package br.com.myclinicpay.data.usecases.payment.expense

import br.com.myclinicpay.domain.model.payment.Expense

interface CreateExpenseRepository {
    fun create(expense: Expense) : Expense
}

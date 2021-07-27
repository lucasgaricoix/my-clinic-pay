package br.com.myclinicpay.data.usecases.payment.expense

import br.com.myclinicpay.domain.model.payment.Expense

interface FindByIdExpenseRepository {
    fun findById(id: String): Expense
}
